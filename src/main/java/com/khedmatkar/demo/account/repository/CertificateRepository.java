package com.khedmatkar.demo.account.repository;

import com.khedmatkar.demo.account.entity.Certificate;
import com.khedmatkar.demo.account.entity.ValidationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {

    List<Certificate> findByStatus(ValidationStatus status);

    List<Certificate> findByStatusAndSpecialtyNameLike(ValidationStatus status, String pattern);
}
