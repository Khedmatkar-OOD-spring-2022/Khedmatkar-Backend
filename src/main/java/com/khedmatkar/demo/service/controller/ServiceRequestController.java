package com.khedmatkar.demo.service.controller;

import com.khedmatkar.demo.service.dto.ServiceRequestDTO;
import com.khedmatkar.demo.service.entity.ServiceRequest;
import com.khedmatkar.demo.service.repository.ServiceRequestRepository;
import com.khedmatkar.demo.service.repository.ServiceTypeRepository;
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
    private final ServiceTypeRepository serviceTypeRepository;

    public ServiceRequestController(ServiceRequestRepository serviceRequestRepository,
                                    ServiceTypeRepository serviceTypeRepository) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.serviceTypeRepository = serviceTypeRepository;
    }

    @PostMapping("/")
    public void post(@RequestBody ServiceRequestDTO dto) {
        var serviceType = serviceTypeRepository.findById(dto.serviceTypeId)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.BAD_REQUEST, "service type has other service types as children"
                        )
                );

        var serviceRequest = ServiceRequest.builder()
                .serviceType(serviceType)
                .description(dto.description)
                .address(dto.address)
                .build();

        serviceRequestRepository.save(serviceRequest);
    }
}
