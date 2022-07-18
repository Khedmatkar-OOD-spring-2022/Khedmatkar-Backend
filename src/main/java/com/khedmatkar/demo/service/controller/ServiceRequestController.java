package com.khedmatkar.demo.service.controller;

import com.khedmatkar.demo.service.entity.ServiceRequestDetailViewDTO;
import com.khedmatkar.demo.service.repository.ServiceRequestRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("api/serviceRequest")
public class ServiceRequestController {
    private final ServiceRequestRepository serviceRequestRepository;

    public ServiceRequestController(ServiceRequestRepository serviceRequestRepository) {
        this.serviceRequestRepository = serviceRequestRepository;
    }

    @GetMapping("/{id}")
    public ServiceRequestDetailViewDTO get(@PathVariable(name = "id") Long id) {
        var serviceRequest = serviceRequestRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "service request not found"
                        ));
        return ServiceRequestDetailViewDTO.from(serviceRequest);
    }
}
