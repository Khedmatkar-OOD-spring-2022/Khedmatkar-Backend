package com.khedmatkar.demo.service.service;

import com.khedmatkar.demo.account.entity.Customer;
import com.khedmatkar.demo.exception.CustomerNotValidException;
import com.khedmatkar.demo.exception.ServiceRequestIsNotPendingForCustomer;
import com.khedmatkar.demo.exception.ServiceRequestNotFoundException;
import com.khedmatkar.demo.messaging.entity.ChatStatus;
import com.khedmatkar.demo.messaging.repository.ChatRepository;
import com.khedmatkar.demo.notification.service.AnnouncementMessage;
import com.khedmatkar.demo.notification.service.AnnouncementService;
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
    private final AnnouncementService announcementService;
    private final ChatRepository chatRepository;

    public ServiceRequestCustomerAcceptanceService(
            ServiceRequestRepository serviceRequestRepository,
            ServiceRequestSpecialistRepository serviceRequestSpecialistRepository,
            ServiceRequestSpecialistFinderService serviceRequestSpecialistFinderService,
            AnnouncementService announcementService, ChatRepository chatRepository) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.serviceRequestSpecialistRepository = serviceRequestSpecialistRepository;
        this.serviceRequestSpecialistFinderService = serviceRequestSpecialistFinderService;
        this.announcementService = announcementService;
        this.chatRepository = chatRepository;
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
    }

    private void rejectRelation(ServiceRequestSpecialist relation) {
        var serviceRequest = relation.getServiceRequest();
        relation.setStatus(ServiceRequestSpecialistStatus.REJECTED_BY_CUSTOMER);
        serviceRequestSpecialistRepository.save(relation);
        serviceRequest.setStatus(ServiceRequestStatus.FINDING_SPECIALIST);
        serviceRequest.setAcceptedSpecialist(null);
        serviceRequestRepository.save(serviceRequest);
        serviceRequestSpecialistFinderService.findSpecialistForServiceRequest(serviceRequest);

        relation.getChat().setStatus(ChatStatus.CLOSED);
        chatRepository.save(relation.getChat());

        announcementService.sendAnnouncementToUser(
                relation.getSpecialist(),
                AnnouncementMessage.CUSTOMER_REJECTS_SPECIALIST_ANNOUNCEMENT,
                relation.getServiceRequest().getId()
        );
    }

    private void acceptRelation(ServiceRequestSpecialist relation) {
        ServiceRequest serviceRequest = relation.getServiceRequest();
        relation.setStatus(ServiceRequestSpecialistStatus.ACCEPTED);
        serviceRequestSpecialistRepository.save(relation);
        serviceRequest.setStatus(ServiceRequestStatus.IN_PROGRESS);
        serviceRequest.setAcceptedSpecialist(relation.getSpecialist());
        serviceRequestRepository.save(serviceRequest);

        announcementService.sendAnnouncementToUser(
                relation.getSpecialist(),
                AnnouncementMessage.CUSTOMER_ACCEPTS_SPECIALIST_ANNOUNCEMENT,
                relation.getServiceRequest().getId()
        );
    }
}
