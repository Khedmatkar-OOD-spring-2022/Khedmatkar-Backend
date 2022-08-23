package com.khedmatkar.demo.account.service;

import com.khedmatkar.demo.account.dto.SpecialistSearchDTO;
import com.khedmatkar.demo.account.entity.Certificate;
import com.khedmatkar.demo.account.entity.Specialist;
import com.khedmatkar.demo.account.entity.ValidationStatus;
import com.khedmatkar.demo.account.repository.CertificateRepository;
import com.khedmatkar.demo.account.repository.SpecialistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpecialistService {
    private final SpecialistRepository specialistRepository;
    private final CertificateRepository certificateRepository;

    public SpecialistService(SpecialistRepository specialistRepository, CertificateRepository certificateRepository) {
        this.specialistRepository = specialistRepository;
        this.certificateRepository = certificateRepository;
    }


    public List<Specialist> searchSpecialists(SpecialistSearchDTO dto) {
        String format = String.format("%%%s%%", dto.specialtyName);
        return certificateRepository.findByStatusAndSpecialtyNameLike(
                ValidationStatus.VALID,
                format
        ).stream()
                .map(Certificate::getSpecialist)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Specialist> getAll() {
        return specialistRepository.findAll();
    }
}
