package com.khedmatkar.demo.service;

import com.khedmatkar.demo.service.ServiceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {

    Page<ServiceType> findByNameStartsWith(@Param("name") String name, Pageable p);
}
