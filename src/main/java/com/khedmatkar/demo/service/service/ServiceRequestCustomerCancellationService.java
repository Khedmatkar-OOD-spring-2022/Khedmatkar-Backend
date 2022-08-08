package com.khedmatkar.demo.service.service;

import com.khedmatkar.demo.account.entity.Customer;
import com.khedmatkar.demo.account.entity.UserType;
import com.khedmatkar.demo.exception.CustomerNotValidException;
import com.khedmatkar.demo.exception.ServiceRequestCancelWrongStateException;
import com.khedmatkar.demo.exception.ServiceRequestNotFoundException;
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

    public ServiceRequestCustomerCancellationService(ServiceRequestRepository serviceRequestRepository) {
        this.serviceRequestRepository = serviceRequestRepository;
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
        // todo: send notification to specialist if in service request is in progress
    }

    public void cancel(ServiceRequest serviceRequest) {
        var nonAcceptableStatuses = Arrays.asList(ServiceRequestStatus.IN_PROGRESS, ServiceRequestStatus.CANCELED, ServiceRequestStatus.DONE);
        if (!nonAcceptableStatuses.contains(serviceRequest.getStatus()))
            serviceRequest.setStatus(ServiceRequestStatus.CANCELED);
        else
            throw new ServiceRequestCancelWrongStateException();
    }
}
