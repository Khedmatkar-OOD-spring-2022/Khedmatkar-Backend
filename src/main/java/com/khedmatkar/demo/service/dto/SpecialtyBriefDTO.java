package com.khedmatkar.demo.service.dto;


import com.khedmatkar.demo.service.entity.Specialty;
import lombok.Builder;

@Builder
public class SpecialtyBriefDTO {
    public Long id;
    public String name;

    public static SpecialtyBriefDTO from(Specialty specialty) {
        return SpecialtyBriefDTO
                .builder()
                .id(specialty.getId())
                .name(specialty.getName())
                .build();
    }
}
