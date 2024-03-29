package com.khedmatkar.demo.account.service;

import com.khedmatkar.demo.account.entity.User;
import com.khedmatkar.demo.account.repository.AdminRepository;
import com.khedmatkar.demo.account.repository.CustomerRepository;
import com.khedmatkar.demo.account.repository.SpecialistRepository;
import com.khedmatkar.demo.account.repository.UserRepository;
import com.khedmatkar.demo.exception.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;


@Component
public class AccountService {
    private final UserRepository userRepository;
    private final SpecialistRepository specialistRepository;
    private final CustomerRepository customerRepository;
    private final AdminRepository adminRepository;

    public AccountService(UserRepository userRepository,
                          SpecialistRepository specialistRepository,
                          CustomerRepository customerRepository,
                          AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.specialistRepository = specialistRepository;
        this.customerRepository = customerRepository;
        this.adminRepository = adminRepository;
    }

    public User findUserFromUserDetails(UserDetails userDetails) {
        String email = userDetails.getUsername();
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public User findConcreteUserClassFromUserDetails(UserDetails userDetails) {
        String email = userDetails.getUsername();
        var specialist = specialistRepository.findByEmail(email);
        if (specialist.isPresent())
            return specialist.get();
        var customer = customerRepository.findByEmail(email);
        if (customer.isPresent())
            return customer.get();
        var admin = adminRepository.findByEmail(email);
        if (admin.isPresent())
            return admin.get();
        throw new UserNotFoundException();
    }
}
