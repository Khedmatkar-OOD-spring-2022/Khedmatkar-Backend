package com.khedmatkar.demo.service.controller;

import com.khedmatkar.demo.account.AccountService;
import com.khedmatkar.demo.account.entity.Customer;
import com.khedmatkar.demo.account.entity.Specialist;
import com.khedmatkar.demo.exception.ServiceRequestNotFoundException;
import com.khedmatkar.demo.exception.ServiceRequestNotRelatedToSpecialistException;
import com.khedmatkar.demo.service.entity.ServiceRequest;
import com.khedmatkar.demo.service.entity.ServiceRequestSpecialist;
import com.khedmatkar.demo.service.entity.ServiceRequestSpecialistStatus;
import com.khedmatkar.demo.service.entity.ServiceRequestStatus;
import com.khedmatkar.demo.service.repository.ServiceRequestRepository;
import com.khedmatkar.demo.service.repository.ServiceRequestSpecialistRepository;
import com.khedmatkar.demo.service.service.ServiceRequestSpecialistFinderService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/serviceRequests")
public class ServiceRequestCustomerController {
    // todo: refactor this class and ServiceRequestCustomerController to code remove duplicates
    // todo: recommendation: convert these two classes to a single service class (not a controller class)
    // todo: and call service class methods from controller

    private final AccountService accountService;
    private final ServiceRequestRepository serviceRequestRepository;
    private final ServiceRequestSpecialistRepository serviceRequestSpecialistRepository;
    private final ServiceRequestSpecialistFinderService serviceRequestSpecialistFinderService;

    public ServiceRequestCustomerController(
            AccountService accountService,
            ServiceRequestRepository serviceRequestRepository,
            ServiceRequestSpecialistRepository serviceRequestSpecialistRepository,
            ServiceRequestSpecialistFinderService serviceRequestSpecialistFinderService) {
        this.accountService = accountService;
        this.serviceRequestRepository = serviceRequestRepository;
        this.serviceRequestSpecialistRepository = serviceRequestSpecialistRepository;
        this.serviceRequestSpecialistFinderService = serviceRequestSpecialistFinderService;
    }


    @PostMapping("/{id}/customer/reject")
    @RolesAllowed("ROLE_CUSTOMER")
    @Transactional
    public void specialistAccept(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails,
            @PathVariable(name = "id") Long id) {

        // todo: remove duplication
        var customer = (Customer) accountService.findConcreteUserClassFromUserDetails(userDetails);
        var serviceRequest = getServiceRequest(id);
        if (!customer.equals(serviceRequest.getCustomer())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "request user not matching service's customer"
            );
        }
        var relation = serviceRequestSpecialistRepository.findByServiceRequestAndStatusEquals(
                serviceRequest,
                ServiceRequestSpecialistStatus.WAITING_FOR_CUSTOMER
        ).orElseThrow(() ->
            new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "service is not waiting for customer's acceptance"
            )
        );
        if (!ServiceRequestStatus.WAITING_FOR_CUSTOMER_ACCEPTANCE.equals(serviceRequest.getStatus())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "bad"
            );
        }

        relation.setStatus(ServiceRequestSpecialistStatus.REJECTED_BY_CUSTOMER);
        serviceRequestSpecialistRepository.save(relation);
        serviceRequest.setStatus(ServiceRequestStatus.FINDING_SPECIALIST);
        serviceRequestRepository.save(serviceRequest);

        // send notification to customer

        serviceRequestSpecialistFinderService.findSpecialistForServiceRequest(serviceRequest);
    }

    @PostMapping("/{id}/customer/accept")
    @RolesAllowed("ROLE_CUSTOMER")
    @Transactional
    public void customerAccept(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails,
            @PathVariable(name = "id") Long id) {
        var customer = (Customer) accountService.findConcreteUserClassFromUserDetails(userDetails);
        var serviceRequest = getServiceRequest(id);
        if (!customer.equals(serviceRequest.getCustomer())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "request user not matching service's customer"
            );
        }
        var relation = serviceRequestSpecialistRepository.findByServiceRequestAndStatusEquals(
                serviceRequest,
                ServiceRequestSpecialistStatus.WAITING_FOR_CUSTOMER
        ).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "service is not waiting for customer's acceptance"
                )
        );

        if (!ServiceRequestStatus.WAITING_FOR_CUSTOMER_ACCEPTANCE.equals(serviceRequest.getStatus())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "bad"
            );
        }

        relation.setStatus(ServiceRequestSpecialistStatus.ACCEPTED);
        serviceRequestSpecialistRepository.save(relation);

        serviceRequest.setStatus(ServiceRequestStatus.IN_PROGRESS);
        serviceRequestRepository.save(serviceRequest);

        // send notification to specialist
    }

    private ServiceRequest getServiceRequest(Long id) {
        return serviceRequestRepository.findById(id)
                .orElseThrow(ServiceRequestNotFoundException::new);
    }


    private ServiceRequestSpecialist getRelation(Specialist specialist, ServiceRequest serviceRequest) {
        return serviceRequestSpecialistRepository.findFirstByServiceRequestAndSpecialistOrderByCreationDesc(
                serviceRequest, specialist).orElseThrow(ServiceRequestNotRelatedToSpecialistException::new);
    }
}
