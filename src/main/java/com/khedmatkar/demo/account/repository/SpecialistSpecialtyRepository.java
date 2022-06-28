package com.khedmatkar.demo.account.repository;

import com.khedmatkar.demo.account.entity.SpecialtyCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialistSpecialtyRepository extends JpaRepository<SpecialtyCertificate, Long> {
}
