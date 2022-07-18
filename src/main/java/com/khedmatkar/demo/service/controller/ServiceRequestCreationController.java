package com.khedmatkar.demo.service.controller;

import com.khedmatkar.demo.service.dto.ServiceRequestCreationDTO;
import com.khedmatkar.demo.service.service.ServiceRequestSpecialistFinderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/serviceRequest")
public class ServiceRequestCreationController {
    private final ServiceRequestSpecialistFinderService serviceRequestSpecialistFinderService;

    public ServiceRequestCreationController(ServiceRequestSpecialistFinderService serviceRequestSpecialistFinderService) {
        this.serviceRequestSpecialistFinderService = serviceRequestSpecialistFinderService;
    }

    @PostMapping("/")
    public void post(@RequestBody ServiceRequestCreationDTO dto) {
        serviceRequestSpecialistFinderService.create(dto);
    }
}
