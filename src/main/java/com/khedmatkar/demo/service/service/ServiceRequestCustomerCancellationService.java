package com.khedmatkar.demo.service.service;

import com.khedmatkar.demo.account.entity.Customer;
import com.khedmatkar.demo.account.entity.Specialist;
import com.khedmatkar.demo.account.entity.UserType;
import com.khedmatkar.demo.exception.CustomerNotValidException;
import com.khedmatkar.demo.exception.ServiceRequestCancelWrongStateException;
import com.khedmatkar.demo.exception.ServiceRequestNotFoundException;
import com.khedmatkar.demo.notification.service.AnnouncementMessage;
import com.khedmatkar.demo.notification.service.AnnouncementService;
import com.khedmatkar.demo.service.entity.ServiceRequest;
import com.khedmatkar.demo.service.entity.ServiceRequestStatus;
import com.khedmatkar.demo.service.repository.ServiceRequestRepository;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import java.util.Arrays;

@Service
public class ServiceRequestCustomerCancellationService {
    private final ServiceRequestRepository serviceRequestRepository;
    private final AnnouncementService announcementService;

    public ServiceRequestCustomerCancellationService(
            ServiceRequestRepository serviceRequestRepository,
            AnnouncementService announcementService) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.announcementService = announcementService;
    }

    @RolesAllowed(UserType.Role.CUSTOMER)
    @Transactional
    public void cancel(Long serviceId, Customer customer) {
        var serviceRequest = serviceRequestRepository.findById(serviceId)
                .orElseThrow(ServiceRequestNotFoundException::new);
        if (!customer.equals(serviceRequest.getCustomer())) {
            throw new CustomerNotValidException();
        }
        cancel(serviceRequest);
        serviceRequestRepository.save(serviceRequest);


        var acceptedSpecialist = serviceRequest.getAcceptedSpecialist();
        if (acceptedSpecialist != null) {
            announcementService.sendAnnouncementToUser(
                    acceptedSpecialist,
                    AnnouncementMessage.CUSTOMER_CANCELS_SERVICE_ANNOUNCEMENT,
                    serviceRequest.getId()
            );
        }
    }

    public void cancel(ServiceRequest serviceRequest) {
        var nonAcceptableStatuses = Arrays.asList(ServiceRequestStatus.IN_PROGRESS, ServiceRequestStatus.CANCELED, ServiceRequestStatus.DONE);
        if (!nonAcceptableStatuses.contains(serviceRequest.getStatus()))
            serviceRequest.setStatus(ServiceRequestStatus.CANCELED);
        else
            throw new ServiceRequestCancelWrongStateException();
    }
}
