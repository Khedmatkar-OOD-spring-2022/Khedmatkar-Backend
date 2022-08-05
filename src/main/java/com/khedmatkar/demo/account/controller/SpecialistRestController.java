package com.khedmatkar.demo.account.controller;

import com.khedmatkar.demo.account.dto.SpecialistDTO;
import com.khedmatkar.demo.account.dto.SpecialistSearchDTO;
import com.khedmatkar.demo.account.service.SpecialistService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/specialists")
public class SpecialistRestController {
    private final SpecialistService specialistService;


    public SpecialistRestController(SpecialistService specialistService) {
        this.specialistService = specialistService;
    }

    @PostMapping("/search")
    public List<SpecialistDTO> searchSpecialists(
            @RequestBody SpecialistSearchDTO dto) {
        return specialistService.searchSpecialists(dto)
                .stream()
                .map(SpecialistDTO::from)
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<SpecialistDTO> getAllSpecialists() {
        return specialistService.getAll()
                .stream()
                .map(SpecialistDTO::from)
                .collect(Collectors.toList());
    }
}
