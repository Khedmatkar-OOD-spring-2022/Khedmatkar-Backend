package com.khedmatkar.demo.account.controller;

import com.khedmatkar.demo.account.dto.UserProfileDTO;
import com.khedmatkar.demo.account.entity.UserType;
import com.khedmatkar.demo.account.service.AccountService;
import com.khedmatkar.demo.account.dto.UserDTO;
import com.khedmatkar.demo.account.repository.UserRepository;
import com.khedmatkar.demo.storage.StorageService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController {
    private final UserRepository userRepository;
    private final AccountService accountService;
    private final StorageService storageService;

    public UserProfileController(UserRepository userRepository,
                                 AccountService accountService,
                                 StorageService storageService) {
        this.userRepository = userRepository;
        this.accountService = accountService;
        this.storageService = storageService;
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
                .profilePicturePath(user.getProfilePicturePath())
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


    @RolesAllowed({UserType.Role.CUSTOMER, UserType.Role.SPECIALIST})
    @PostMapping("/upload")
    public void uploadProfile(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails,
            @RequestParam(name = "file") MultipartFile file) throws IOException {
        var user = accountService.findUserFromUserDetails(userDetails);
        String filePath = storageService.storeFile(file);
        user.setProfilePicturePath(filePath);
        userRepository.save(user);
    }
}
