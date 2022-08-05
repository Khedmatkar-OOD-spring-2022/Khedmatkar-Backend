package com.khedmatkar.demo.configuration.controller;

import com.khedmatkar.demo.account.entity.AdminPermission;
import com.khedmatkar.demo.configuration.dto.ConfigEntryDTO;
import com.khedmatkar.demo.configuration.service.ConfigEntryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/configs")
public class ConfigEntryRestController {
    private final ConfigEntryService configEntryService;

    public ConfigEntryRestController(ConfigEntryService configEntryService) {
        this.configEntryService = configEntryService;
    }

    @PostMapping("")
    @RolesAllowed(AdminPermission.Role.ROOT)
    public void updateConfigEntry(@RequestBody ConfigEntryDTO dto) {
        configEntryService.updateConfigEntry(dto);
    }
}
