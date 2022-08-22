package com.khedmatkar.demo.service.service;

import com.khedmatkar.demo.notification.service.AnnouncementMessage;
import com.khedmatkar.demo.notification.service.AnnouncementService;
import com.khedmatkar.demo.service.entity.ServiceRequest;
import com.khedmatkar.demo.service.entity.ServiceRequestStatus;
import org.springframework.stereotype.Service;

@Service
public class ServiceRequestNoSpecialistCancellerService {
    private final AnnouncementService announcementService;

    public ServiceRequestNoSpecialistCancellerService(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    public void noSpecialistCancel(ServiceRequest serviceRequest) {
        var customer = serviceRequest.getCustomer();
        announcementService.sendAnnouncementWithEmailToUser(
                customer,
                AnnouncementMessage.SERVICE_REQUEST_CANCELED_DUE_TO_LACK_OF_SPECIALISTS,
                customer.getFirstName() + " " + customer.getLastName(),
                serviceRequest.getId()
        );
        serviceRequest.setStatus(ServiceRequestStatus.CANCELED);
    }
}
