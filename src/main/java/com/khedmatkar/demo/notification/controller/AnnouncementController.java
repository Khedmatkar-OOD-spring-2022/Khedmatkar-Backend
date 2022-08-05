package com.khedmatkar.demo.notification.controller;

import com.khedmatkar.demo.account.entity.User;
import com.khedmatkar.demo.account.entity.UserType;
import com.khedmatkar.demo.account.service.AccountService;
import com.khedmatkar.demo.notification.dto.AnnouncementDTO;
import com.khedmatkar.demo.notification.service.AnnouncementService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {
    private final AccountService accountService;
    private final AnnouncementService announcementService;

    public AnnouncementController(AccountService accountService,
                                  AnnouncementService announcementService) {
        this.accountService = accountService;
        this.announcementService = announcementService;
    }

    @GetMapping("/")
    @RolesAllowed(UserType.Role.USER)
    public List<AnnouncementDTO> getAnnouncements(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails) {
        User user = accountService.findUserFromUserDetails(userDetails);
        return announcementService.getUserAnnouncements(user)
                .stream()
                .map(AnnouncementDTO::from)
                .collect(Collectors.toList());
    }
}
