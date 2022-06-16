package com.khedmatkar.demo.account;


import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegistrationManager {
    private final UserRepository userRepository;

    public RegistrationManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @PostMapping("/register")
    public void registerUser(@RequestBody @Valid UserRegistrationDTO dto) {
        var type = dto.type.toUpperCase();
        var userType = UserType.valueOf(type);
        var delegatingPasswordEncoder =
                PasswordEncoderFactories.createDelegatingPasswordEncoder();
        var encodedPassword = delegatingPasswordEncoder.encode(dto.password);
        User user = User.builder()
                .email(dto.email)
                .firstName(dto.firstName)
                .lastName(dto.lastName)
                .password(encodedPassword)
                .description(dto.description)
                .type(userType)
                .build();
        userRepository.save(user);
    }
}
