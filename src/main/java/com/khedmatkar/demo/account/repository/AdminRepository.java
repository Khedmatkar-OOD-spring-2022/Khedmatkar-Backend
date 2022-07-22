package com.khedmatkar.demo.account.repository;

import com.khedmatkar.demo.account.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

//    @Query(value = "select * from admin_permissions p join admins a on a.id = p.admin_id join users u on a.id = u.id;", nativeQuery = true)
    Optional<Admin> findByEmail(String email);
}
