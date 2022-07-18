package com.khedmatkar.demo.account.repository;

import com.khedmatkar.demo.account.entity.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SpecialistRepository extends JpaRepository<Specialist, Long> {

    Optional<Specialist> findByEmail(String email);

    @Query(value = "select * from specialists s inner join users u on s.id = u.id order by random() limit 1;", nativeQuery = true)
    Optional<Specialist> findRandomSpecialist();
}
