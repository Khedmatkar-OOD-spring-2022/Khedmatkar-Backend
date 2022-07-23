package com.khedmatkar.demo.service.dto;

import com.khedmatkar.demo.AbstractEntity;
import com.khedmatkar.demo.account.dto.UserProfileDTO;
import com.khedmatkar.demo.service.entity.ServiceRequest;
import com.khedmatkar.demo.service.entity.ServiceRequestStatus;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@NoArgsConstructor
@SuperBuilder
public class ServiceRequestListViewDTO {
    public Long id;
    public ServiceRequestStatus status;
    public LocalDateTime creation;
    public String address;
    public Date receptionDate;
    public SpecialtyDTO specialty;
    public UserProfileDTO specialist;

    public static ServiceRequestListViewDTO from(ServiceRequest serviceRequest) {
        return ServiceRequestListViewDTO.builder()
                .id(serviceRequest.getId())
                .status(serviceRequest.getStatus())
                .address(serviceRequest.getAddress())
                .creation(serviceRequest.getCreation())
                .receptionDate(serviceRequest.getReceptionDate())
                .specialty(SpecialtyDTO.from(serviceRequest.getSpecialty()))
                .specialist(
                        Optional.ofNullable(serviceRequest.getAcceptedSpecialist())
                                .map(UserProfileDTO::from)
                                .orElse(null))
                .build();
    }
}
