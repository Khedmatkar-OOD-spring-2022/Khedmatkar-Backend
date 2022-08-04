package com.khedmatkar.demo.account.dto;

import com.khedmatkar.demo.account.entity.Specialist;


public class SpecialistDTO extends UserProfileDTO {
    public Long id;

    public SpecialistDTO(Specialist specialist) {
        super(specialist.getFirstName(), specialist.getLastName(), specialist.getEmail());
    }


    public static SpecialistDTO from(Specialist specialist) {
        var dto = new SpecialistDTO(specialist);
        dto.id = specialist.getId();
        return dto;
    }
}
