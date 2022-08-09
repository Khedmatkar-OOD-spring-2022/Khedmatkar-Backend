package com.khedmatkar.demo.account.dto;

import com.khedmatkar.demo.account.entity.Certificate;
import com.khedmatkar.demo.account.entity.Specialist;
import com.khedmatkar.demo.account.entity.ValidationStatus;
import com.khedmatkar.demo.service.dto.SpecialtyBriefDTO;

import java.util.List;
import java.util.stream.Collectors;


public class SpecialistDTO extends UserProfileDTO {
    public Long id;
    public List<SpecialtyBriefDTO> specialty;

    public SpecialistDTO(Specialist specialist) {
        super(specialist.getFirstName(), specialist.getLastName(), specialist.getEmail());
    }


    public static SpecialistDTO from(Specialist specialist) {
        var dto = new SpecialistDTO(specialist);
        dto.id = specialist.getId();
        dto.specialty = specialist.getCertificateSet()
                .stream()
                .filter(x -> ValidationStatus.VALID.equals(x.getStatus()))
                .map(Certificate::getSpecialty)
                .map(SpecialtyBriefDTO::from)
                .collect(Collectors.toList());
        return dto;
    }
}
