package com.khedmatkar.demo.account.repository;

import com.khedmatkar.demo.account.entity.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SpecialistRepository extends JpaRepository<Specialist, Long> {

    Optional<Specialist> findByEmail(String email);

    @Query(value = "select specialist from Specialist specialist order by RAND() limit 1;", nativeQuery = true)
    Optional<Specialist> findRandomSpecialist();



//    @Query("select specialist from Specialist specialist where specialist.id not serviceRequest.", )
//    Optional<Specialist> getAllByRelatedServiceRequestsIdIn(ServiceRequest serviceRequest);
}
