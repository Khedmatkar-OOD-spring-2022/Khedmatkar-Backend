package com.khedmatkar.demo.service.service;

import com.khedmatkar.demo.account.entity.AdminPermission;
import com.khedmatkar.demo.account.entity.UserType;
import com.khedmatkar.demo.exception.ServiceRequestNotFoundException;
import com.khedmatkar.demo.service.entity.ServiceRequest;
import com.khedmatkar.demo.service.entity.ServiceRequestStatus;
import com.khedmatkar.demo.service.repository.ServiceRequestRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;

@Service
public class ServiceRequestAdminCancellationService {
    private final ServiceRequestRepository serviceRequestRepository;

    public ServiceRequestAdminCancellationService(ServiceRequestRepository serviceRequestRepository) {
        this.serviceRequestRepository = serviceRequestRepository;
    }

    @RolesAllowed(AdminPermission.Role.SERVICE_W)
    @Transactional
    public void cancel(Long serviceId) {
        var serviceRequest = serviceRequestRepository.findById(serviceId)
                .orElseThrow(ServiceRequestNotFoundException::new);
        cancel(serviceRequest);
        serviceRequestRepository.save(serviceRequest);
        // todo: send notification to customer
        // todo: send notification to specialist if in service request is in progress
    }

    public void cancel(ServiceRequest serviceRequest) {
        serviceRequest.setStatus(ServiceRequestStatus.CANCELED);
    }
}
