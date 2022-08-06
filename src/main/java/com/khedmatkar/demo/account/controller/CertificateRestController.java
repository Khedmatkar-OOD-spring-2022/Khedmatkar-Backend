package com.khedmatkar.demo.account.controller;

import com.khedmatkar.demo.account.dto.CertificateProfileDTO;
import com.khedmatkar.demo.account.entity.*;
import com.khedmatkar.demo.account.service.AccountService;
import com.khedmatkar.demo.account.repository.CertificateRepository;
import com.khedmatkar.demo.exception.CertificateNotFoundException;
import com.khedmatkar.demo.exception.SpecialistNotFoundException;
import com.khedmatkar.demo.service.repository.SpecialtyRepository;
import com.khedmatkar.demo.storage.StorageService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/certificates")
public class CertificateRestController {
    private final CertificateRepository certificateRepository;
    private final SpecialtyRepository specialtyRepository;
    private final AccountService accountService;
    private final StorageService storageService;
    private final HttpServletRequest request;


    public CertificateRestController(
            CertificateRepository certificateRepository,
            SpecialtyRepository specialtyRepository,
            AccountService accountService, StorageService storageService, HttpServletRequest request) {
        this.certificateRepository = certificateRepository;
        this.specialtyRepository = specialtyRepository;
        this.accountService = accountService;
        this.storageService = storageService;
        this.request = request;
    }


    @PostMapping("/{certificateId}/validate")
    @RolesAllowed(AdminPermission.Role.VALIDATE_CERTIFICATE_W)
    public void validateSpecialty(@PathVariable(name = "certificateId") Long certificateId) {
        setValidStatus(certificateId, ValidationStatus.VALID);
    }

    @PostMapping("/{certificateId}/invalidate")
    @RolesAllowed(AdminPermission.Role.VALIDATE_CERTIFICATE_W)
    public void invalidateSpecialty(@PathVariable(name = "certificateId") Long certificateId) {
        setValidStatus(certificateId, ValidationStatus.INVALID);
    }

    @GetMapping("/all")
    @RolesAllowed(AdminPermission.Role.VALIDATE_CERTIFICATE_W)
    public List<CertificateProfileDTO> getPendingCertificates(
            @RequestParam(name = "status", required = false) ValidationStatus status) {
        List<Certificate> certificates;
        if (status == null) {
            certificates = certificateRepository.findAll();
        } else {
            certificates = certificateRepository.findByStatus(status);
        }
        return certificates
                .stream()
                .map(CertificateProfileDTO::from)
                .collect(Collectors.toList());
    }


    private void setValidStatus(Long certificateId, ValidationStatus status) {
        Certificate certificate = certificateRepository.findById(certificateId)
                .orElseThrow(CertificateNotFoundException::new);
        certificate.setStatus(status);
        certificateRepository.save(certificate);
    }

    @GetMapping("/")
    @RolesAllowed(UserType.Role.SPECIALIST)
    @Transactional
    public List<CertificateProfileDTO> getAll(@AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails) {
        var specialist = (Specialist) accountService.findConcreteUserClassFromUserDetails(userDetails);
        return specialist.getCertificateSet().stream()
                .map(CertificateProfileDTO::from)
                .collect(Collectors.toList());
    }

    @PostMapping("/")
    @Secured({"ROLE_SPECIALIST"})
    public void addCertificate(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails,
            @RequestParam(name = "file") MultipartFile file,
            @RequestParam(name = "specialtyId") Long id) throws IOException {

        var specialist = (Specialist) accountService.findConcreteUserClassFromUserDetails(userDetails);

        var specialty = specialtyRepository.findById(id)
                .orElseThrow(SpecialistNotFoundException::new);

        var path = storageService.storeFile(file);

        Certificate certificate = Certificate.builder()
                .specialist(specialist)
                .specialty(specialty)
                .status(ValidationStatus.PENDING)
                .filePath(path)
                .build();

        certificateRepository.save(certificate);
    }

    @DeleteMapping("/{certificateId}")
    @Transactional
    public void removeCertificate(@PathVariable Long certificateId,
                                  @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails) {
        var specialist = (Specialist) accountService.findConcreteUserClassFromUserDetails(userDetails);

        Certificate certificate = certificateRepository.findById(certificateId)
                .orElseThrow(CertificateNotFoundException::new);
        if (!specialist.equals(certificate.getSpecialist())) {
            throw new CertificateNotFoundException();
        }
        certificateRepository.delete(certificate);
    }
}
