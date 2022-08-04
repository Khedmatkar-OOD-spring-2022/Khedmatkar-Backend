package com.khedmatkar.demo.account.service;

import com.khedmatkar.demo.account.dto.SpecialistSearchDTO;
import com.khedmatkar.demo.account.entity.Specialist;
import com.khedmatkar.demo.account.repository.SpecialistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialistService {
    private final SpecialistRepository specialistRepository;

    public SpecialistService(SpecialistRepository specialistRepository) {
        this.specialistRepository = specialistRepository;
    }


    public List<Specialist> searchSpecialists(SpecialistSearchDTO dto) {
        return specialistRepository.findByCertificateSetSpecialtyNameContains(dto.specialtyName);
    }
}
