package com.khedmatkar.demo.service.repository;

import com.khedmatkar.demo.account.entity.Customer;
import com.khedmatkar.demo.service.entity.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {


    List<ServiceRequest> findAllByCustomer(Customer customer);


    List<ServiceRequest> findAllByCustomerAndCreationAfterAndAddressContains(
            Customer customer,
            LocalDateTime localDateTime,
            String addressKeyword);


    // select * from service_requests sr where sr.customer_id = 10 and creation::date >= '2021-12-12'
    //
}
