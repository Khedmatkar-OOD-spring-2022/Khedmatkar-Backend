package com.khedmatkar.demo.account;


import com.khedmatkar.demo.repository.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationManager {
    private final UserRepository userRepository;

    public RegistrationManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @PostMapping("/register")
    public void registerUser(@RequestBody UserRegistrationDTO dto) {
        User user = User.builder()
                .email(dto.email)
                .plainPassword(dto.plainPassword)
                .description(dto.description)
                .build();
        userRepository.save(user);
    }
}
