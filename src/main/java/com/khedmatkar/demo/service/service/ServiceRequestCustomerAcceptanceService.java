package com.khedmatkar.demo.service.service;

import com.khedmatkar.demo.account.entity.Customer;
import com.khedmatkar.demo.exception.CustomerNotValidException;
import com.khedmatkar.demo.exception.ServiceRequestIsNotPendingForCustomer;
import com.khedmatkar.demo.exception.ServiceRequestNotFoundException;
import com.khedmatkar.demo.service.domain.ServiceRequestAcceptanceCommand;
import com.khedmatkar.demo.service.entity.ServiceRequest;
import com.khedmatkar.demo.service.entity.ServiceRequestSpecialist;
import com.khedmatkar.demo.service.entity.ServiceRequestSpecialistStatus;
import com.khedmatkar.demo.service.entity.ServiceRequestStatus;
import com.khedmatkar.demo.service.repository.ServiceRequestRepository;
import com.khedmatkar.demo.service.repository.ServiceRequestSpecialistRepository;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;

@Service
public class ServiceRequestCustomerAcceptanceService {
    private final ServiceRequestRepository serviceRequestRepository;
    private final ServiceRequestSpecialistRepository serviceRequestSpecialistRepository;
    private final ServiceRequestSpecialistFinderService serviceRequestSpecialistFinderService;

    public ServiceRequestCustomerAcceptanceService(
            ServiceRequestRepository serviceRequestRepository,
            ServiceRequestSpecialistRepository serviceRequestSpecialistRepository,
            ServiceRequestSpecialistFinderService serviceRequestSpecialistFinderService) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.serviceRequestSpecialistRepository = serviceRequestSpecialistRepository;
        this.serviceRequestSpecialistFinderService = serviceRequestSpecialistFinderService;
    }

    @RolesAllowed("ROLE_CUSTOMER")
    @Transactional
    public void handleAcceptOrReject(ServiceRequestAcceptanceCommand command) {
        var serviceRequest = serviceRequestRepository.findById(command.getServiceRequestId())
                .orElseThrow(ServiceRequestNotFoundException::new);
        var customer = (Customer) command.getUser();
        if (!customer.equals(serviceRequest.getCustomer())) {
            throw new CustomerNotValidException();
        }
        if (!ServiceRequestStatus.WAITING_FOR_CUSTOMER_ACCEPTANCE.equals(serviceRequest.getStatus())) {
            throw new ServiceRequestIsNotPendingForCustomer();
        }
        var relation = serviceRequestSpecialistRepository.findByServiceRequestAndStatusEquals(
                serviceRequest,
                ServiceRequestSpecialistStatus.WAITING_FOR_CUSTOMER_ACCEPTANCE
        ).orElseThrow();

        if (ServiceRequestAcceptanceCommand.AcceptanceAction.ACCEPT.equals(command.getAction())) {
            acceptRelation(relation);
        } else {
            rejectRelation(relation);
        }

        // todo: send notification to specialist
    }

    private void rejectRelation(ServiceRequestSpecialist relation) { //todo close chat
        var serviceRequest = relation.getServiceRequest();
        relation.setStatus(ServiceRequestSpecialistStatus.REJECTED_BY_CUSTOMER);
        serviceRequestSpecialistRepository.save(relation);
        serviceRequest.setStatus(ServiceRequestStatus.FINDING_SPECIALIST);
        serviceRequestRepository.save(serviceRequest);
        serviceRequestSpecialistFinderService.findSpecialistForServiceRequest(serviceRequest);
    }

    private void acceptRelation(ServiceRequestSpecialist relation) {
        ServiceRequest serviceRequest = relation.getServiceRequest();
        relation.setStatus(ServiceRequestSpecialistStatus.ACCEPTED);
        serviceRequestSpecialistRepository.save(relation);
        serviceRequest.setStatus(ServiceRequestStatus.IN_PROGRESS);
        serviceRequestRepository.save(serviceRequest);
    }
}
