package com.khedmatkar.demo.service.repository;

import com.khedmatkar.demo.account.entity.Specialist;
import com.khedmatkar.demo.service.entity.ServiceRequest;
import com.khedmatkar.demo.service.entity.ServiceRequestSpecialist;
import com.khedmatkar.demo.service.entity.ServiceRequestSpecialistStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ServiceRequestSpecialistRepository extends JpaRepository<ServiceRequestSpecialist, Long> {

    List<ServiceRequestSpecialist> findAllBySpecialist(Specialist specialist);

    Optional<ServiceRequestSpecialist> findFirstByServiceRequestAndSpecialistOrderByCreationDesc(
            ServiceRequest serviceRequest, Specialist specialist);

    Optional<ServiceRequestSpecialist> findByServiceRequestAndStatusEquals(ServiceRequest serviceRequest, ServiceRequestSpecialistStatus status);
}
