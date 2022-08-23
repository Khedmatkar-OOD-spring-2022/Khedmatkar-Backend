package com.khedmatkar.demo.account.controller;


import com.khedmatkar.demo.account.dto.UserDTO;
import com.khedmatkar.demo.account.entity.*;
import com.khedmatkar.demo.account.repository.AdminRepository;
import com.khedmatkar.demo.account.repository.CustomerRepository;
import com.khedmatkar.demo.account.repository.SpecialistRepository;
import com.khedmatkar.demo.account.repository.UserRepository;
import com.khedmatkar.demo.exception.BadUserTypeException;
import com.khedmatkar.demo.exception.UserAlreadyExistsException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegistrationController {
    private final CustomerRepository customerRepository;
    private final SpecialistRepository specialistRepository;
    private final AdminRepository adminRepository;
    private final UserRepository userRepository;

    public RegistrationController(
            CustomerRepository customerRepository,
            SpecialistRepository specialistRepository,
            UserRepository userRepository,
            AdminRepository adminRepository) {
        this.customerRepository = customerRepository;
        this.specialistRepository = specialistRepository;
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
    }


    @PostMapping("/register")
    public void registerUser(@RequestBody @Valid UserDTO dto) {
        if (userRepository.existsByEmail(dto.email)) {
            throw new UserAlreadyExistsException();
        }

        var user = fillUserInfo(dto);
        var userType = user.getType();
        if (UserType.CUSTOMER.equals(userType)) {
            customerRepository.save((Customer) user);
        } else if (UserType.SPECIALIST.equals(userType)) {
            specialistRepository.save((Specialist) user);
        } else if (UserType.ADMIN.equals(userType)) {
            adminRepository.save((Admin) user);
        }
    }

    public User fillUserInfo(UserDTO dto) {
        User user = null;
        UserType userType = null;
        String typeString = dto.type.toUpperCase();
        if (UserType.CUSTOMER.toString().equals(typeString)) {
            userType = UserType.CUSTOMER;
            user = new Customer();
        } else if (UserType.SPECIALIST.toString().equals(typeString)) {
            userType = UserType.SPECIALIST;
            user = new Specialist();
        } else if (UserType.ADMIN.toString().equals(typeString)) {
            userType = UserType.ADMIN;
            user = new Admin();
        } else {
            throw new BadUserTypeException();
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
