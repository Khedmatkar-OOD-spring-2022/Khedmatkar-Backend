package com.khedmatkar.demo.account;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Locale;

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
        User user = User.builder()
                .email(dto.email)
                .firstName(dto.firstName)
                .lastName(dto.lastName)
                .plainPassword(dto.plainPassword)
                .description(dto.description)
                .type(userType)
                .build();
        userRepository.save(user);
    }
}
