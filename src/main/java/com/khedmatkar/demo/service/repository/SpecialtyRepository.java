package com.khedmatkar.demo.service.repository;

import com.khedmatkar.demo.service.entity.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {

    boolean existsByParent(Specialty specialty);

    List<Specialty> findByNameStartsWith(String name);
}
