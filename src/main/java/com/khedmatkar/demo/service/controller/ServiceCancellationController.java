package com.khedmatkar.demo.service.controller;


import com.khedmatkar.demo.account.entity.AdminPermission;
import com.khedmatkar.demo.account.entity.Customer;
import com.khedmatkar.demo.account.entity.UserType;
import com.khedmatkar.demo.account.service.AccountService;
import com.khedmatkar.demo.service.service.ServiceRequestAdminCancellationService;
import com.khedmatkar.demo.service.service.ServiceRequestCustomerCancellationService;
import org.springframework.context.annotation.Role;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;

@RestController
@RequestMapping("api/serviceRequests")
public class ServiceCancellationController {
    private final AccountService accountService;
    private final ServiceRequestAdminCancellationService serviceRequestAdminCancellationService;
    private final ServiceRequestCustomerCancellationService serviceRequestCustomerCancellationService;

    public ServiceCancellationController(
            AccountService accountService,
            ServiceRequestAdminCancellationService serviceRequestAdminCancellationService,
            ServiceRequestCustomerCancellationService serviceRequestCustomerCancellationService) {
        this.accountService = accountService;
        this.serviceRequestAdminCancellationService = serviceRequestAdminCancellationService;
        this.serviceRequestCustomerCancellationService = serviceRequestCustomerCancellationService;
    }

    @PostMapping("/{id}/customer/cancel")
    @RolesAllowed(UserType.Role.CUSTOMER)
    @Transactional
    public void customerCancel(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails,
            @PathVariable(name = "id") Long id) {
        var customer = (Customer) accountService.findConcreteUserClassFromUserDetails(userDetails);
        serviceRequestCustomerCancellationService.cancel(id, customer);
    }

    @PostMapping("/{id}/admin/cancel")
    @RolesAllowed(AdminPermission.Role.SERVICE_W)
    @Transactional
    public void adminCancel(@PathVariable(name = "id") Long id) {
        serviceRequestAdminCancellationService.cancel(id);
    }
}
