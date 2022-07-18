package com.khedmatkar.demo.service.notdomain;

import com.khedmatkar.demo.account.entity.Specialist;
import com.khedmatkar.demo.account.entity.User;
import com.khedmatkar.demo.service.entity.ServiceRequest;
import com.khedmatkar.demo.service.entity.ServiceRequestSpecialist;
import com.khedmatkar.demo.service.repository.ServiceRequestSpecialistRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SpecialistServiceRequestHistoryFinder implements ServiceRequestFinder{
    private final ServiceRequestSpecialistRepository serviceRequestSpecialistRepository;

    public SpecialistServiceRequestHistoryFinder(
            ServiceRequestSpecialistRepository serviceRequestSpecialistRepository) {
        this.serviceRequestSpecialistRepository = serviceRequestSpecialistRepository;
    }

    @Override
    public List<ServiceRequest> getServiceRequests(User user) {
        return serviceRequestSpecialistRepository.findAllBySpecialist((Specialist) user)
                .stream()
                .map(ServiceRequestSpecialist::getServiceRequest)
                .collect(Collectors.toList());
    }
}
