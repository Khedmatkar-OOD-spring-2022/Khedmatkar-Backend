package com.khedmatkar.demo.account.controller;

import com.khedmatkar.demo.account.entity.User;
import com.khedmatkar.demo.account.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController {
    private final UserRepository userRepository;

    public UserProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    @Secured("ROLE_USER")
    public User getProfile(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        String email = user.getUsername();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "entity not found"
        ));
    }
}
