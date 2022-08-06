package com.khedmatkar.demo.account.dto;

import com.khedmatkar.demo.account.entity.Certificate;
import com.khedmatkar.demo.account.entity.ValidationStatus;
import com.khedmatkar.demo.service.dto.SpecialtyDTO;
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
    public ValidationStatus status;
    public SpecialtyDTO specialtyDTO;
    public String filePath;

    public static CertificateProfileDTO from(Certificate certificate) {
        return CertificateProfileDTO.builder()
                .id(certificate.getId())
                .specialtyId(certificate.getSpecialty().getId())
                .status(certificate.getStatus())
                .specialtyDTO(SpecialtyDTO.from(certificate.getSpecialty()))
                .filePath(certificate.getFilePath())
                .build();
    }
}
