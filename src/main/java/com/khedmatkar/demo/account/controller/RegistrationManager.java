package com.khedmatkar.demo.account.controller;


import com.khedmatkar.demo.account.dto.UserRegistrationDTO;
import com.khedmatkar.demo.account.entity.Customer;
import com.khedmatkar.demo.account.entity.Specialist;
import com.khedmatkar.demo.account.entity.User;
import com.khedmatkar.demo.account.entity.UserType;
import com.khedmatkar.demo.account.repository.CustomerRepository;
import com.khedmatkar.demo.account.repository.SpecialistRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
public class RegistrationManager {
    private final CustomerRepository customerRepository;
    private final SpecialistRepository specialistRepository;

    public RegistrationManager(
            CustomerRepository customerRepository,
            SpecialistRepository specialistRepository) {
        this.customerRepository = customerRepository;
        this.specialistRepository = specialistRepository;
    }


    @PostMapping("/register")
    public void registerUser(@RequestBody @Valid UserRegistrationDTO dto) {
        var user = fillUserInfo(dto);
        var userType = user.getType();
        if (UserType.CUSTOMER.equals(userType)) {
            customerRepository.save((Customer) user);
        } else if (UserType.SPECIALIST.equals(userType)) {
            specialistRepository.save((Specialist) user);
        }
    }

    public User fillUserInfo(UserRegistrationDTO dto) {
        User user = null;
        UserType userType = null;
        String typeString = dto.type.toUpperCase();
        if (UserType.CUSTOMER.toString().equals(typeString)) {
            userType = UserType.CUSTOMER;
            user = new Customer();
        } else if (UserType.SPECIALIST.toString().equals(typeString)) {
            userType = UserType.SPECIALIST;
            user = new Specialist();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "bad user type"
            );
        }

        var encodedPassword = PasswordEncoderFactories
                .createDelegatingPasswordEncoder()
                .encode(dto.password);

        user.setEmail(dto.email);
        user.setFirstName(dto.firstName);
        user.setLastName(dto.lastName);
        user.setPassword(encodedPassword);
        user.setType(userType);
        return user;
    }
}
