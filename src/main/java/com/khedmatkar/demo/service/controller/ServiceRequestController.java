package com.khedmatkar.demo.service.controller;

import com.khedmatkar.demo.exception.ServiceRequestNotFoundException;
import com.khedmatkar.demo.service.dto.ServiceRequestListViewDTO;
import com.khedmatkar.demo.service.repository.ServiceRequestRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;


@RestController
@RequestMapping("api/serviceRequests")
public class ServiceRequestController {
    private final ServiceRequestRepository serviceRequestRepository;

    public ServiceRequestController(ServiceRequestRepository serviceRequestRepository) {
        this.serviceRequestRepository = serviceRequestRepository;
    }

    @GetMapping("/{id}")
    @Transactional
    public ServiceRequestListViewDTO get(@PathVariable(name = "id") Long id) {
        var serviceRequest = serviceRequestRepository.findById(id)
                .orElseThrow(ServiceRequestNotFoundException::new);
        return ServiceRequestListViewDTO.from(serviceRequest);
    }
}
