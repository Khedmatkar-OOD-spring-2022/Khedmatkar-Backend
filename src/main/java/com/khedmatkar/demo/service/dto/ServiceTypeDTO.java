package com.khedmatkar.demo.service.dto;

import javax.validation.constraints.NotBlank;

public class ServiceTypeDTO {

    @NotBlank
    public String name;
    
    public Long parentId;
}
