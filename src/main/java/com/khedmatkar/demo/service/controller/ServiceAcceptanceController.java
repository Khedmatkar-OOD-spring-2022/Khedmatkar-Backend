package com.khedmatkar.demo.service.controller;


import com.khedmatkar.demo.account.entity.UserType;
import com.khedmatkar.demo.account.service.AccountService;
import com.khedmatkar.demo.account.entity.Customer;
import com.khedmatkar.demo.account.entity.Specialist;
import com.khedmatkar.demo.service.domain.ServiceRequestAcceptanceCommand;
import com.khedmatkar.demo.service.service.ServiceRequestCustomerAcceptanceService;
import com.khedmatkar.demo.service.service.ServiceRequestSpecialistAcceptanceService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;

@RestController
@RequestMapping("api/serviceRequests")
public class ServiceAcceptanceController {
    private final AccountService accountService;
    private final ServiceRequestCustomerAcceptanceService customerAcceptanceService;
    private final ServiceRequestSpecialistAcceptanceService specialistAcceptanceService;

    public ServiceAcceptanceController(
            AccountService accountService,
            ServiceRequestCustomerAcceptanceService customerAcceptanceService,
            ServiceRequestSpecialistAcceptanceService specialistAcceptanceService) {
        this.accountService = accountService;
        this.customerAcceptanceService = customerAcceptanceService;
        this.specialistAcceptanceService = specialistAcceptanceService;
    }

    @RolesAllowed(UserType.Role.SPECIALIST)
    @PostMapping("/{id}/specialist/accept")
    @Transactional
    public void specialistAccept(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails,
            @PathVariable(name = "id") Long id) {
        var specialist = (Specialist) accountService.findConcreteUserClassFromUserDetails(userDetails);
        var command = ServiceRequestAcceptanceCommand.builder()
                .user(specialist)
                .action(ServiceRequestAcceptanceCommand.AcceptanceAction.ACCEPT)
                .serviceRequestId(id)
                .build();
        specialistAcceptanceService.handleSpecialistAcceptOrReject(command);
    }


    @RolesAllowed(UserType.Role.SPECIALIST)
    @PostMapping("/{id}/specialist/reject")
    @Transactional
    public void specialistReject(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails,
            @PathVariable(name = "id") Long id) {
        var specialist = (Specialist) accountService.findConcreteUserClassFromUserDetails(userDetails);
        var command = ServiceRequestAcceptanceCommand.builder()
                .user(specialist)
                .action(ServiceRequestAcceptanceCommand.AcceptanceAction.REJECT)
                .serviceRequestId(id)
                .build();
        specialistAcceptanceService.handleSpecialistAcceptOrReject(command);
    }


    @RolesAllowed(UserType.Role.CUSTOMER)
    @PostMapping("/{id}/customer/accept")
    @Transactional
    public void customerAccept(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails,
            @PathVariable(name = "id") Long id) {
        var customer = (Customer) accountService.findConcreteUserClassFromUserDetails(userDetails);
        var command = ServiceRequestAcceptanceCommand.builder()
                .user(customer)
                .action(ServiceRequestAcceptanceCommand.AcceptanceAction.ACCEPT)
                .serviceRequestId(id)
                .build();
        customerAcceptanceService.handleAcceptOrReject(command);
    }


    @RolesAllowed(UserType.Role.CUSTOMER)
    @PostMapping("/{id}/customer/reject")
    @Transactional
    public void customerReject(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails,
            @PathVariable(name = "id") Long id) {
        var customer = (Customer) accountService.findConcreteUserClassFromUserDetails(userDetails);
        var command = ServiceRequestAcceptanceCommand.builder()
                .user(customer)
                .action(ServiceRequestAcceptanceCommand.AcceptanceAction.REJECT)
                .serviceRequestId(id)
                .build();
        customerAcceptanceService.handleAcceptOrReject(command);
    }
}
