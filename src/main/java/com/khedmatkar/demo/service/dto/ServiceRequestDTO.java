package com.khedmatkar.demo.service.dto;

import javax.validation.constraints.NotNull;

public class ServiceRequestDTO {
    @NotNull
    public Long serviceTypeId;

    public String description;

    @NotNull
    public String address;
}
