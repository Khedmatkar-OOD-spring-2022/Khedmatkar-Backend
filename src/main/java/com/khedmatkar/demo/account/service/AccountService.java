package com.khedmatkar.demo.account.service;

import com.khedmatkar.demo.account.entity.User;
import com.khedmatkar.demo.account.repository.CustomerRepository;
import com.khedmatkar.demo.account.repository.SpecialistRepository;
import com.khedmatkar.demo.account.repository.UserRepository;
import com.khedmatkar.demo.exception.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Component
public class AccountService {
    private final UserRepository userRepository;
    private final SpecialistRepository specialistRepository;
    private final CustomerRepository customerRepository;

    public AccountService(UserRepository userRepository,
                          SpecialistRepository specialistRepository,
                          CustomerRepository customerRepository) {
        this.userRepository = userRepository;
        this.specialistRepository = specialistRepository;
        this.customerRepository = customerRepository;
    }

    public User findUserFromUserDetails(UserDetails userDetails) {
        String email = userDetails.getUsername();
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

    public User findConcreteUserClassFromUserDetails(UserDetails userDetails) {
        String email = userDetails.getUsername();
        var specialist = specialistRepository.findByEmail(email);
        if (specialist.isPresent())
            return specialist.get();
        var customer = customerRepository.findByEmail(email);
        if (customer.isPresent())
            return customer.get();

        throw new UserNotFoundException();
    }
}
