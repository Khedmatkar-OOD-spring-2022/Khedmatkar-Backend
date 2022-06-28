package com.khedmatkar.demo.account.controller;

import com.khedmatkar.demo.account.dto.UserProfileDTO;
import com.khedmatkar.demo.account.entity.User;
import com.khedmatkar.demo.account.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController {
    private final UserRepository userRepository;

    public UserProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("")
    @RolesAllowed("ROLE_USER")
    public UserProfileDTO getProfile(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails) {
        var user = getUser(userDetails);
        return UserProfileDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .type(user.getType().toString())
                .build();
    }

    @PostMapping("/changePassword")
    @RolesAllowed("ROLE_USER")
    public void changePassword(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails,
            UserProfileDTO dto) {

        var user = getUser(userDetails);
        var newPassword = PasswordEncoderFactories
                .createDelegatingPasswordEncoder()
                .encode(dto.password);
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    private User getUser(UserDetails userDetails) {
        String email = userDetails.getUsername();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "entity not found"
                ));
    }
}
