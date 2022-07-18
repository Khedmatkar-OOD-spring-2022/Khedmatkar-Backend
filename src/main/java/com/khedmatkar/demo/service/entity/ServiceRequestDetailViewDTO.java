package com.khedmatkar.demo.service.entity;

import com.khedmatkar.demo.account.entity.Customer;
import com.khedmatkar.demo.account.entity.Specialist;
import com.khedmatkar.demo.service.dto.ServiceRequestCreationDTO;
import com.khedmatkar.demo.service.dto.SpecialtyBriefDTO;
import lombok.Builder;

import javax.persistence.*;
import java.util.List;


@Builder
public class ServiceRequestDetailViewDTO {
    public Long id;
    public SpecialtyBriefDTO specialtyBriefDTO;
    public String description;
    public String address;

    public static ServiceRequestDetailViewDTO from(ServiceRequest serviceRequest) {
        return ServiceRequestDetailViewDTO.builder()
                .id(serviceRequest.getId())
                .specialtyBriefDTO(SpecialtyBriefDTO.from(serviceRequest.getSpecialty()))
                .description(serviceRequest.getDescription())
                .address(serviceRequest.getAddress())
                .build();
    }
}
