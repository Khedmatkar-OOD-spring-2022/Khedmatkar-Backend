package com.khedmatkar.demo.account.repository;

import com.khedmatkar.demo.account.entity.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialistRepository extends JpaRepository<Specialist, Long> {
}
