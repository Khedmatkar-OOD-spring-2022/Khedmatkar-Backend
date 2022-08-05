package com.khedmatkar.demo.service.service;

import com.khedmatkar.demo.account.entity.Specialist;
import com.khedmatkar.demo.exception.ServiceRequestIsNotInSpecialistPendingStateException;
import com.khedmatkar.demo.exception.ServiceRequestNotFoundException;
import com.khedmatkar.demo.exception.ServiceRequestNotRelatedToSpecialistException;
import com.khedmatkar.demo.messaging.entity.Chat;
import com.khedmatkar.demo.messaging.entity.ChatStatus;
import com.khedmatkar.demo.messaging.repository.ChatRepository;
import com.khedmatkar.demo.notification.service.AnnouncementMessage;
import com.khedmatkar.demo.notification.service.AnnouncementService;
import com.khedmatkar.demo.service.domain.ServiceRequestAcceptanceCommand;
import com.khedmatkar.demo.service.entity.ServiceRequestSpecialist;
import com.khedmatkar.demo.service.entity.ServiceRequestSpecialistStatus;
import com.khedmatkar.demo.service.entity.ServiceRequestStatus;
import com.khedmatkar.demo.service.repository.ServiceRequestRepository;
import com.khedmatkar.demo.service.repository.ServiceRequestSpecialistRepository;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;

@Service
public class ServiceRequestSpecialistAcceptanceService {
    private final ServiceRequestRepository serviceRequestRepository;
    private final ServiceRequestSpecialistRepository serviceRequestSpecialistRepository;
    private final ServiceRequestSpecialistFinderService serviceRequestSpecialistFinderService;
    private final ChatRepository chatRepository;
    private final AnnouncementService announcementService;

    public ServiceRequestSpecialistAcceptanceService(
            ServiceRequestRepository serviceRequestRepository,
            ServiceRequestSpecialistRepository serviceRequestSpecialistRepository,
            ServiceRequestSpecialistFinderService serviceRequestSpecialistFinderService,
            ChatRepository chatRepository,
            AnnouncementService announcementService) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.serviceRequestSpecialistRepository = serviceRequestSpecialistRepository;
        this.serviceRequestSpecialistFinderService = serviceRequestSpecialistFinderService;
        this.chatRepository = chatRepository;
        this.announcementService = announcementService;
    }

    @RolesAllowed("ROLE_SPECIALIST")
    @Transactional
    public void handleSpecialistAcceptOrReject(ServiceRequestAcceptanceCommand command) {
        var serviceRequest = serviceRequestRepository.findById(command.getServiceRequestId())
                .orElseThrow(ServiceRequestNotFoundException::new);
        var specialist = (Specialist) command.getUser();
        var relation =
                serviceRequestSpecialistRepository
                        .findFirstByServiceRequestAndSpecialistOrderByCreationDesc(serviceRequest, specialist)
                        .orElseThrow(ServiceRequestNotRelatedToSpecialistException::new);
        if (!ServiceRequestSpecialistStatus.WAITING_FOR_SPECIALIST_ACCEPTANCE.equals(relation.getStatus())) {
            throw new ServiceRequestIsNotInSpecialistPendingStateException();
        }

        if (ServiceRequestAcceptanceCommand.AcceptanceAction.ACCEPT.equals(command.getAction())) {
            accept(relation);
        } else {
            reject(relation);
        }
    }

    private void accept(ServiceRequestSpecialist relation) {
        var serviceRequest = relation.getServiceRequest();
        relation.setStatus(ServiceRequestSpecialistStatus.WAITING_FOR_CUSTOMER_ACCEPTANCE);
        Chat chat = Chat.builder()
                .status(ChatStatus.OPENED)
                .serviceRequestSpecialist(relation)
                .userA(serviceRequest.getCustomer())
                .userB(relation.getSpecialist())
                .build();
        chatRepository.save(chat);
        relation.setChat(chat);
        serviceRequest.setStatus(ServiceRequestStatus.WAITING_FOR_CUSTOMER_ACCEPTANCE);
        serviceRequestRepository.save(serviceRequest);
        serviceRequestSpecialistRepository.save(relation);

        announcementService.sendAnnouncementToUser(
                serviceRequest.getCustomer(),
                AnnouncementMessage.SPECIALIST_ACCEPTS_SERVICE_ANNOUNCEMENT
                        .getMessage()
                        .formatted(serviceRequest.getId())
        );
    }

    private void reject(ServiceRequestSpecialist relation) {
        var serviceRequest = relation.getServiceRequest();
        relation.setStatus(ServiceRequestSpecialistStatus.REJECTED_BY_SPECIALIST);
        serviceRequest.setStatus(ServiceRequestStatus.FINDING_SPECIALIST);
        serviceRequestRepository.save(serviceRequest);
        serviceRequestSpecialistRepository.save(relation);
        serviceRequestSpecialistFinderService.findSpecialistForServiceRequest(serviceRequest);

        announcementService.sendAnnouncementToUser(
                serviceRequest.getCustomer(),
                AnnouncementMessage.SPECIALIST_REJECTS_SERVICE_ANNOUNCEMENT
                        .getMessage()
                        .formatted(serviceRequest.getId())
        );
    }
}
