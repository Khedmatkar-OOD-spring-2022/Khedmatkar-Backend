package com.khedmatkar.demo.account.controller;

import com.khedmatkar.demo.account.dto.AdminDTO;
import com.khedmatkar.demo.account.dto.AdminPermissionDTO;
import com.khedmatkar.demo.account.entity.*;
import com.khedmatkar.demo.account.dto.UserDTO;
import com.khedmatkar.demo.account.repository.AdminRepository;
import com.khedmatkar.demo.exception.AdminNotFoundException;
import com.khedmatkar.demo.notification.service.AnnouncementMessage;
import com.khedmatkar.demo.notification.service.AnnouncementService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RolesAllowed(UserType.Role.ADMIN)
@RequestMapping("/api/admins")
public class AdminController {
    private final AdminRepository adminRepository;
    private final RegistrationController registrationController;
    private final AnnouncementService announcementService;

    public AdminController(AdminRepository adminRepository,
                           RegistrationController registrationController,
                           AnnouncementService announcementService) {
        this.adminRepository = adminRepository;
        this.announcementService = announcementService;
        this.registrationController = registrationController;
    }

    @GetMapping("/")
    @RolesAllowed(AdminPermission.Role.USER_LIST_RW)
    @Transactional
    public List<AdminDTO> getAllAdmins() {
        return adminRepository.findAll().stream().map(admin -> AdminDTO.builder()
                .email(admin.getEmail())
                .firstName(admin.getFirstName())
                .lastName(admin.getLastName())
                .permissions(admin.getPermissionsFromString())
                .build()).collect(Collectors.toList());
    }

    @PostMapping("/register")
    @RolesAllowed(AdminPermission.Role.USER_PROFILE_RW)
    @Transactional
    public AdminDTO registerAdmin(@RequestBody @Valid AdminDTO dto) {
        var password = dto.firstName; // todo generate random strong password
        UserDTO userDTO = UserDTO.builder()
                .email(dto.email)
                .firstName(dto.firstName)
                .lastName(dto.lastName)
                .password(password)
                .type(UserType.ADMIN.name())
                .build();
        registrationController.registerUser(userDTO); //todo call user generation service method
        var admin = adminRepository.findByEmail(dto.email)
                .orElseThrow();
        admin.setPermissionsFromString(dto.permissions);
        adminRepository.save(admin);

        announcementService.sendAnnouncementWithEmailToUser(
                admin,
                AnnouncementMessage.ADMIN_CREATION_ANNOUNCEMENT,
                admin.getFirstName(),
                password
        );

        return AdminDTO.builder()
                .firstName(admin.getFirstName())
                .lastName(admin.getLastName())
                .email(admin.getEmail())
                .password(password)
                .permissions(admin.getPermissionsFromString())
                .build();
    }

    @GetMapping("/permission")
    @Transactional
    public AdminPermissionDTO getPermissions(@AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails) {
        var admin = adminRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(AdminNotFoundException::new);
        return AdminPermissionDTO.builder()
                .email(admin.getEmail())
                .permissions(admin.getPermissionsFromString())
                .build();
    }

    @PostMapping("/permission")
    @RolesAllowed(AdminPermission.Role.USER_PROFILE_RW)
    @Transactional
    public void updatePermissions(@RequestBody @Valid AdminPermissionDTO dto) {
        var admin = adminRepository.findByEmail(dto.email)
                .orElseThrow(AdminNotFoundException::new);
        admin.setPermissionsFromString(dto.permissions);
        adminRepository.save(admin);

        announcementService.sendAnnouncementWithEmailToUser(
                admin,
                AnnouncementMessage.ADMIN_PERMISSIONS_UPDATE_ANNOUNCEMENT
        );
    }
}
