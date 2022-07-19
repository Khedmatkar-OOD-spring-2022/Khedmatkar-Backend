package com.khedmatkar.demo.service.dto;

import com.khedmatkar.demo.service.entity.ServiceRequest;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@SuperBuilder
public class ServiceRequestListViewDTO {
    public Long id;
    public LocalDateTime creation;
    public String address;
    public Date receptionDate;

    public static ServiceRequestListViewDTO from(ServiceRequest serviceRequest) {
        return ServiceRequestListViewDTO.builder()
                .id(serviceRequest.getId())
                .address(serviceRequest.getAddress())
                .creation(serviceRequest.getCreation())
                .receptionDate(serviceRequest.getReceptionDate())
                .build();
    }
}
