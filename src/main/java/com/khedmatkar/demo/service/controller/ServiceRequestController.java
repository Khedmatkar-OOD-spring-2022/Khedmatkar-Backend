package com.khedmatkar.demo.service.controller;

import com.khedmatkar.demo.service.dto.ServiceRequestDTO;
import com.khedmatkar.demo.service.entity.ServiceRequest;
import com.khedmatkar.demo.service.repository.ServiceRequestRepository;
import com.khedmatkar.demo.service.repository.SpecialtyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/serviceRequest")
public class ServiceRequestController {

    private final ServiceRequestRepository serviceRequestRepository;
    private final SpecialtyRepository specialtyRepository;

    public ServiceRequestController(ServiceRequestRepository serviceRequestRepository,
                                    SpecialtyRepository specialtyRepository) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.specialtyRepository = specialtyRepository;
    }

    @PostMapping("/")
    public void post(@RequestBody ServiceRequestDTO dto) {
        var specialty = specialtyRepository.findById(dto.specialtyId)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.BAD_REQUEST, "service type has other service types as children"
                        )
                );

        var serviceRequest = ServiceRequest.builder()
                .specialty(specialty)
                .description(dto.description)
                .address(dto.address)
                .build();

        serviceRequestRepository.save(serviceRequest);
    }
}
