package com.khedmatkar.demo.service.dto;

import com.khedmatkar.demo.AbstractEntity;
import com.khedmatkar.demo.service.entity.Specialty;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@SuperBuilder
public class SpecialtyDTO {

    @NotBlank
    public String name;
    
    public Long parentId;

    public static SpecialtyDTO from(Specialty specialty) {
        return SpecialtyDTO.builder()
                .name(specialty.getName())
                .parentId(
                        Optional.ofNullable(specialty.getParent())
                                .map(AbstractEntity::getId)
                                .orElse(null)
                )
                .build();
    }
}
