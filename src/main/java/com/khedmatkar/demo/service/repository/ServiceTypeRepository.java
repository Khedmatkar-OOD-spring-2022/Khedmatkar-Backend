package com.khedmatkar.demo.service.repository;

import com.khedmatkar.demo.service.entity.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {

    boolean existsByParent(ServiceType serviceType);

    List<ServiceType> findByNameStartsWith(String name);
}
