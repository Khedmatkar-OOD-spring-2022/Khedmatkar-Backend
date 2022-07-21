package com.khedmatkar.demo.account.controller;

import com.khedmatkar.demo.account.dto.UserProfileDTO;
import com.khedmatkar.demo.account.service.AccountService;
import com.khedmatkar.demo.account.dto.UserDTO;
import com.khedmatkar.demo.account.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController {
    private final UserRepository userRepository;
    private final AccountService accountService;

    public UserProfileController(UserRepository userRepository,
                                 AccountService accountService) {
        this.userRepository = userRepository;
        this.accountService = accountService;
    }

    @GetMapping("")
    @RolesAllowed("ROLE_USER")
    public UserDTO getProfile(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails) {
        var user = accountService.findUserFromUserDetails(userDetails);
        return UserDTO.builder()
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
            @RequestBody UserDTO dto) {

        var user = accountService.findUserFromUserDetails(userDetails);
        var newPassword = PasswordEncoderFactories
                .createDelegatingPasswordEncoder()
                .encode(dto.password);
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    @PostMapping("")
    @RolesAllowed("ROLE_USER")
    public void updateProfile(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails,
            @RequestBody @Valid UserProfileDTO dto) {
        var user = accountService.findUserFromUserDetails(userDetails);
        user.updateProfile(dto);
        userRepository.save(user);
    }
}
