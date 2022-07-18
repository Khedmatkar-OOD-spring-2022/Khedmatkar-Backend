package com.khedmatkar.demo.service.dto;

import javax.validation.constraints.NotNull;

public class ServiceRequestCreationDTO {
    @NotNull
    public Long specialtyId;

    public String description;

    @NotNull
    public String address;
}
