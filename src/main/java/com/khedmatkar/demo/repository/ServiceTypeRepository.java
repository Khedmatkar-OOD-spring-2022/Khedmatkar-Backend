package com.khedmatkar.demo.repository;

import com.khedmatkar.demo.entity.ServiceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource
public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {

    @RestResource(path = "nameStartsWith", rel = "nameStartsWith")
    Page<ServiceType> findByNameStartsWith(@Param("name") String name, Pageable p);
}
