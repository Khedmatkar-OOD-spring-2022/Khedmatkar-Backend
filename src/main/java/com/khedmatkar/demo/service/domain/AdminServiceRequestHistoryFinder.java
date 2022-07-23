package com.khedmatkar.demo.service.domain;

import com.khedmatkar.demo.account.entity.AdminPermission;
import com.khedmatkar.demo.account.entity.Specialist;
import com.khedmatkar.demo.account.entity.User;
import com.khedmatkar.demo.service.entity.ServiceRequest;
import com.khedmatkar.demo.service.entity.ServiceRequestSpecialist;
import com.khedmatkar.demo.service.repository.ServiceRequestSpecialistRepository;
import org.springframework.stereotype.Component;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AdminServiceRequestHistoryFinder implements ServiceRequestFinder {
    private final ServiceRequestSpecialistRepository serviceRequestSpecialistRepository;

    public AdminServiceRequestHistoryFinder(
            ServiceRequestSpecialistRepository serviceRequestSpecialistRepository) {
        this.serviceRequestSpecialistRepository = serviceRequestSpecialistRepository;
    }

    @Override
    @RolesAllowed(AdminPermission.Role.SERVICE_W)
    public List<ServiceRequest> getServiceRequests(User user) {
        return serviceRequestSpecialistRepository.findAll()
                .stream()
                .map(ServiceRequestSpecialist::getServiceRequest)
                .collect(Collectors.toList());
    }
}
