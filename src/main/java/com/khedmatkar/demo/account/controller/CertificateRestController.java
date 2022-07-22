package com.khedmatkar.demo.account.controller;

import com.khedmatkar.demo.account.dto.CertificateProfileDTO;
import com.khedmatkar.demo.account.entity.AdminPermission;
import com.khedmatkar.demo.account.entity.UserType;
import com.khedmatkar.demo.account.service.AccountService;
import com.khedmatkar.demo.account.dto.CertificateDTO;
import com.khedmatkar.demo.account.entity.Certificate;
import com.khedmatkar.demo.account.entity.Specialist;
import com.khedmatkar.demo.account.repository.CertificateRepository;
import com.khedmatkar.demo.service.dto.SpecialtyDTO;
import com.khedmatkar.demo.service.repository.SpecialtyRepository;
import com.khedmatkar.demo.ticket.dto.FeedbackDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/certificates")
public class CertificateRestController {

    private final CertificateRepository certificateRepository;
    private final SpecialtyRepository specialtyRepository;
    private final AccountService accountService;

    public CertificateRestController(
            CertificateRepository certificateRepository,
            SpecialtyRepository specialtyRepository,
            AccountService accountService) {
        this.certificateRepository = certificateRepository;
        this.specialtyRepository = specialtyRepository;
        this.accountService = accountService;
    }


    @PostMapping("/{certificateId}/validate")
    @RolesAllowed(AdminPermission.Role.VALIDATE_CERTIFICATE_W)
    public void validateSpecialty(@PathVariable(name = "certificateId") Long certificateId) {
        // todo: check the requester is admin and has access
        setValidStatus(certificateId, true);
    }

    @PostMapping("/{certificateId}/invalidate")
    @RolesAllowed(AdminPermission.Role.VALIDATE_CERTIFICATE_W)
    public void invalidateSpecialty(@PathVariable(name = "certificateId") Long certificateId) {
        // todo: check the request is admin and has access
        setValidStatus(certificateId, false);
    }


    private void setValidStatus(Long certificateId, boolean validStatus) {
        Certificate certificate = certificateRepository.findById(certificateId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "certificate not found"
                        ));
        certificate.setValidated(validStatus);
        certificateRepository.save(certificate);
    }

    @GetMapping("/")
    @RolesAllowed(UserType.Role.SPECIALIST)
    @Transactional
    public List<CertificateProfileDTO> getAll(@AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails) {
        var specialist = (Specialist) accountService.findConcreteUserClassFromUserDetails(userDetails);
        return specialist.getCertificateSet().stream()
                .map(certificate -> CertificateProfileDTO.builder()
                        .id(certificate.getId())
                        .specialtyId(certificate.getSpecialty().getId())
                        .validated(certificate.getValidated())
                        .build())
                .collect(Collectors.toList());
    }

    @PostMapping("/")
    @Secured({"ROLE_SPECIALIST"})
    public void addCertificate(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails,
            @RequestBody CertificateDTO dto) {

        var specialist = (Specialist) accountService.findConcreteUserClassFromUserDetails(userDetails);

        var specialty = specialtyRepository.findById(dto.specialtyId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.BAD_REQUEST,
                                "specialty not found"
                        ));

        Certificate certificate = Certificate.builder()
                .specialist(specialist)
                .specialty(specialty)
                .build();

        certificateRepository.save(certificate);
    }

    @DeleteMapping("/{certificateId}")
    @Transactional
    public void removeCertificate(@PathVariable Long certificateId,
                                  @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails) {
        var specialist = (Specialist) accountService.findConcreteUserClassFromUserDetails(userDetails);

        Certificate certificate = certificateRepository.findById(certificateId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.FORBIDDEN,
                                "certificate not found"
                        ));
        if (!specialist.equals(certificate.getSpecialist())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "certificate not found"
            );
        }
        certificateRepository.delete(certificate);
    }
}
