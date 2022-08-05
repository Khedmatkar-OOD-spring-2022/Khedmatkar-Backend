package com.khedmatkar.demo.service.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class ServiceRequestCreationDTO {
    @NotNull
    public Long specialtyId;
    public String description;

    @NotNull
    public String address;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public Date receptionDate;

    public Long specialistId;
}
