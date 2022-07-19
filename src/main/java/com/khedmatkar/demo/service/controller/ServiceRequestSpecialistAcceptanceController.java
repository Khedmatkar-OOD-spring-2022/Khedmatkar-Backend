package com.khedmatkar.demo.service.controller;

import com.khedmatkar.demo.account.AccountService;
import com.khedmatkar.demo.account.entity.Specialist;
import com.khedmatkar.demo.exception.ServiceRequestIsNotInSpecialistPendingStateException;
import com.khedmatkar.demo.exception.ServiceRequestNotFoundException;
import com.khedmatkar.demo.exception.ServiceRequestNotRelatedToSpecialistException;
import com.khedmatkar.demo.service.entity.ServiceRequest;
import com.khedmatkar.demo.service.entity.ServiceRequestSpecialist;
import com.khedmatkar.demo.service.entity.ServiceRequestSpecialistStatus;
import com.khedmatkar.demo.service.entity.ServiceRequestStatus;
import com.khedmatkar.demo.service.repository.ServiceRequestRepository;
import com.khedmatkar.demo.service.repository.ServiceRequestSpecialistRepository;
import com.khedmatkar.demo.service.service.ServiceRequestSpecialistFinderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;

@RestController
@RequestMapping("api/serviceRequests")
public class ServiceRequestSpecialistAcceptanceController {
    private final AccountService accountService;
    private final ServiceRequestRepository serviceRequestRepository;
    private final ServiceRequestSpecialistRepository serviceRequestSpecialistRepository;
    private final ServiceRequestSpecialistFinderService serviceRequestSpecialistFinderService;

    public ServiceRequestSpecialistAcceptanceController(
            AccountService accountService,
            ServiceRequestRepository serviceRequestRepository,
            ServiceRequestSpecialistRepository serviceRequestSpecialistRepository,
            ServiceRequestSpecialistFinderService serviceRequestSpecialistFinderService) {
        this.accountService = accountService;
        this.serviceRequestRepository = serviceRequestRepository;
        this.serviceRequestSpecialistRepository = serviceRequestSpecialistRepository;
        this.serviceRequestSpecialistFinderService = serviceRequestSpecialistFinderService;
    }

    @PostMapping("/{id}/specialist/accept")
    @RolesAllowed("ROLE_SPECIALIST")
    @Transactional
    public void specialistAccept(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails,
            @PathVariable(name = "id") Long id) {
        var specialist = getSpecialist(userDetails);
        var serviceRequest = getServiceRequest(id);
        var relation = getRelation(specialist, serviceRequest);
        if (!ServiceRequestSpecialistStatus.WAITING_FOR_SPECIALIST.equals(relation.getStatus())) {
            throw new ServiceRequestIsNotInSpecialistPendingStateException();
        }
        relation.setStatus(ServiceRequestSpecialistStatus.WAITING_FOR_CUSTOMER);
        serviceRequest.setStatus(ServiceRequestStatus.WAITING_FOR_CUSTOMER_ACCEPTANCE);
        serviceRequestRepository.save(serviceRequest);
        serviceRequestSpecialistRepository.save(relation);

        // send notification to customer
    }

    @PostMapping("/{id}/specialist/reject")
    @RolesAllowed("ROLE_SPECIALIST")
    @Transactional
    public void specialistReject(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails,
            @PathVariable(name = "id") Long id) {
        var specialist = getSpecialist(userDetails);
        var serviceRequest = getServiceRequest(id);
        var relation = getRelation(specialist, serviceRequest);
        if (!relation.getStatus().equals(ServiceRequestSpecialistStatus.WAITING_FOR_SPECIALIST)) {
            throw new ServiceRequestIsNotInSpecialistPendingStateException();
        }
        relation.setStatus(ServiceRequestSpecialistStatus.REJECTED_BY_SPECIALIST);
        serviceRequest.setStatus(ServiceRequestStatus.FINDING_SPECIALIST);
        serviceRequestRepository.save(serviceRequest);
        serviceRequestSpecialistRepository.save(relation);

        // todo: send notification to customer

        serviceRequestSpecialistFinderService.findSpecialistForServiceRequest(serviceRequest);
    }

    private Specialist getSpecialist(UserDetails userDetails) {
        return (Specialist) accountService.findConcreteUserClassFromUserDetails(userDetails);
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
