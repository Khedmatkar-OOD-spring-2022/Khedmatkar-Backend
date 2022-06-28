package com.khedmatkar.demo.account.controller;

import com.khedmatkar.demo.account.dto.SpecialistSpecialtyDTO;
import com.khedmatkar.demo.account.entity.SpecialtyCertificate;
import com.khedmatkar.demo.account.repository.SpecialistRepository;
import com.khedmatkar.demo.account.repository.SpecialistSpecialtyRepository;
import com.khedmatkar.demo.service.repository.SpecialtyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/specialties")
public class SpecialistSpecialtyRestController {

    private final SpecialistRepository specialistRepository;
    private final SpecialistSpecialtyRepository specialistSpecialtyRepository;
    private final SpecialtyRepository specialtyRepository;

    public SpecialistSpecialtyRestController(
            SpecialistRepository specialistRepository,
            SpecialistSpecialtyRepository specialistSpecialtyRepository,
            SpecialtyRepository specialtyRepository) {
        this.specialistRepository = specialistRepository;
        this.specialistSpecialtyRepository = specialistSpecialtyRepository;
        this.specialtyRepository = specialtyRepository;
    }


    @PostMapping("/{specialtyId}/validate")
    @Secured({"ROLE_SPECIALIST"})
    public void validateSpecialty(@PathVariable(name = "specialtyId") Long specialtyId) {
        setValidStatus(specialtyId, true);
    }

    @PostMapping("/{specialtyId}/invalidate")
    @Secured({"ROLE_SPECIALIST"})
    public void invalidateSpecialty(@PathVariable(name = "specialtyId") Long specialistId) {
        setValidStatus(specialistId, false);
    }


    private void setValidStatus(Long specialtyId, boolean validStatus) {
        SpecialtyCertificate certificate = specialistSpecialtyRepository.findById(specialtyId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "speciality not found"
                        ));
        certificate.setValidated(validStatus);
        specialistSpecialtyRepository.save(certificate);
    }

    @PostMapping("/specialists/{specialistId}")
    @Secured({"ROLE_SPECIALIST"})
    public void addSpeciality(
            @PathVariable(name = "specialistId") Long specialistId,
            @RequestBody SpecialistSpecialtyDTO dto) {

        var specialist = specialistRepository.findById(specialistId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "specialist not found"
                        ));

        var specialty = specialtyRepository.findById(dto.specialtyId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "specialty not found"
                        ));

        SpecialtyCertificate certificate = SpecialtyCertificate.builder()
                .specialist(specialist)
                .specialty(specialty)
                .build();

        specialistSpecialtyRepository.save(certificate);
    }


}
