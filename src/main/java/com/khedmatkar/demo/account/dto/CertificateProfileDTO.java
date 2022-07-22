package com.khedmatkar.demo.account.dto;

import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;

@AllArgsConstructor
@SuperBuilder
public class CertificateProfileDTO {

    public Long id;

    @Email
    public String specialistEmail;

    public Long specialtyId;

    public Boolean validated;
}
