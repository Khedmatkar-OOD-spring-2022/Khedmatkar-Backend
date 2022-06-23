package com.khedmatkar.demo.account.repository;

import com.khedmatkar.demo.account.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
