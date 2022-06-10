package com.khedmatkar.demo.service.repository;

import com.khedmatkar.demo.service.entity.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {
}
