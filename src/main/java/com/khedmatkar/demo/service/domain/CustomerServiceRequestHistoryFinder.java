package com.khedmatkar.demo.service.domain;

import com.khedmatkar.demo.account.entity.Customer;
import com.khedmatkar.demo.account.entity.User;
import com.khedmatkar.demo.service.entity.ServiceRequest;
import com.khedmatkar.demo.service.repository.ServiceRequestRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerServiceRequestHistoryFinder implements ServiceRequestFinder {

    private final ServiceRequestRepository serviceRequestRepository;

    public CustomerServiceRequestHistoryFinder(ServiceRequestRepository serviceRequestRepository) {
        this.serviceRequestRepository = serviceRequestRepository;
    }

    @Override
    public List<ServiceRequest> getServiceRequests(User user) {
        return serviceRequestRepository.findAllByCustomer((Customer) user);
    }
}
