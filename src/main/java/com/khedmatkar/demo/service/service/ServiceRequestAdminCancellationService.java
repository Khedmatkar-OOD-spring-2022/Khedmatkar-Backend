package com.khedmatkar.demo.service.service;

import com.khedmatkar.demo.account.entity.AdminPermission;
import com.khedmatkar.demo.account.entity.Specialist;
import com.khedmatkar.demo.exception.ServiceRequestNotFoundException;
import com.khedmatkar.demo.notification.service.AnnouncementMessage;
import com.khedmatkar.demo.notification.service.AnnouncementService;
import com.khedmatkar.demo.service.entity.ServiceRequest;
import com.khedmatkar.demo.service.entity.ServiceRequestStatus;
import com.khedmatkar.demo.service.repository.ServiceRequestRepository;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;

@Service
public class ServiceRequestAdminCancellationService {
    private final ServiceRequestRepository serviceRequestRepository;
    private final AnnouncementService announcementService;

    public ServiceRequestAdminCancellationService(
            ServiceRequestRepository serviceRequestRepository,
            AnnouncementService announcementService) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.announcementService = announcementService;
    }

    @RolesAllowed(AdminPermission.Role.SERVICE_W)
    @Transactional
    public void cancel(Long serviceId) {
        var serviceRequest = serviceRequestRepository.findById(serviceId)
                .orElseThrow(ServiceRequestNotFoundException::new);
        cancel(serviceRequest);
        serviceRequestRepository.save(serviceRequest);

        announcementService.sendAnnouncementToUser(
                serviceRequest.getCustomer(),
                AnnouncementMessage.ADMIN_CANCELS_SERVICE_ANNOUNCEMENT,
                serviceId
                );

        var acceptedSpecialist = serviceRequest.getAcceptedSpecialist();
        if (acceptedSpecialist != null) {
            announcementService.sendAnnouncementToUser(
                    acceptedSpecialist,
                    AnnouncementMessage.ADMIN_CANCELS_SERVICE_ANNOUNCEMENT,
                    serviceId
            );
        }
    }

    public void cancel(ServiceRequest serviceRequest) {
        serviceRequest.setStatus(ServiceRequestStatus.CANCELED);
    }
}
