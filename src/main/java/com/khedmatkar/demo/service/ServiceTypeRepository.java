package com.khedmatkar.demo.service;

import com.khedmatkar.demo.service.ServiceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {

    List<ServiceType> findByNameStartsWith(String name);
}
