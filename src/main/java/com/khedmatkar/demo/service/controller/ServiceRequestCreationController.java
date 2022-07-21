package com.khedmatkar.demo.service.controller;

import com.khedmatkar.demo.account.service.AccountService;
import com.khedmatkar.demo.account.entity.Customer;
import com.khedmatkar.demo.service.dto.ServiceRequestCreationDTO;
import com.khedmatkar.demo.service.service.ServiceRequestSpecialistFinderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("api/serviceRequest")
public class ServiceRequestCreationController {
    private final ServiceRequestSpecialistFinderService serviceRequestSpecialistFinderService;
    private final AccountService accountService;

    public ServiceRequestCreationController(ServiceRequestSpecialistFinderService serviceRequestSpecialistFinderService, AccountService accountService) {
        this.serviceRequestSpecialistFinderService = serviceRequestSpecialistFinderService;
        this.accountService = accountService;
    }

    @PostMapping("/")
    public void post(@RequestBody @Valid ServiceRequestCreationDTO dto,
                     @AuthenticationPrincipal UserDetails userDetails) {
        var customer = (Customer) accountService.findConcreteUserClassFromUserDetails(userDetails);
        serviceRequestSpecialistFinderService.create(dto, customer);
    }
}
