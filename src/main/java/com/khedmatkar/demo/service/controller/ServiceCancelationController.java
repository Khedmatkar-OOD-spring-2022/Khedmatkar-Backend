package com.khedmatkar.demo.service.controller;


import com.khedmatkar.demo.account.entity.Customer;
import com.khedmatkar.demo.account.service.AccountService;
import com.khedmatkar.demo.service.service.ServiceRequestAdminCancellationService;
import com.khedmatkar.demo.service.service.ServiceRequestCustomerCancellationService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping("api/serviceRequests")
public class ServiceCancelationController {
    private final AccountService accountService;
    private final ServiceRequestAdminCancellationService serviceRequestAdminCancellationService;
    private final ServiceRequestCustomerCancellationService serviceRequestCustomerCancellationService;

    public ServiceCancelationController(
            AccountService accountService,
            ServiceRequestAdminCancellationService serviceRequestAdminCancellationService,
            ServiceRequestCustomerCancellationService serviceRequestCustomerCancellationService) {
        this.accountService = accountService;
        this.serviceRequestAdminCancellationService = serviceRequestAdminCancellationService;
        this.serviceRequestCustomerCancellationService = serviceRequestCustomerCancellationService;
    }

    @PostMapping("/{id}/customer/cancel")
    @Transactional
    public void specialistAccept(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails,
            @PathVariable(name = "id") Long id) {
        var customer = (Customer) accountService.findConcreteUserClassFromUserDetails(userDetails);
        serviceRequestCustomerCancellationService.cancel(id, customer);
    }

    @PostMapping("/{id}/admin/cancel")
    @Transactional
    public void specialistReject(@PathVariable(name = "id") Long id) {
        serviceRequestAdminCancellationService.cancel(id);
    }
}
