package com.khedmatkar.demo.service.controller;

import com.khedmatkar.demo.service.dto.SpecialtyDTO;
import com.khedmatkar.demo.service.entity.Specialty;
import com.khedmatkar.demo.service.repository.SpecialtyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/specialties")
public class SpecialtyRestController {

    private final SpecialtyRepository specialtyRepository;

    public SpecialtyRestController(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }


    @GetMapping("/{id}")
    public Specialty getById(@PathVariable Long id) {
        var byId = specialtyRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "entity not found"
        );
    }

    @GetMapping("")
    public List<Specialty> getAll() {
        return specialtyRepository.findAll();
    }

    @GetMapping("/search")
    public List<Specialty> searchByName(@PathParam(value = "name") String name) {
        return specialtyRepository.findByNameStartsWith(name);
    }

    @PostMapping
    public void post(@RequestBody SpecialtyDTO dto) {
        var specialty = Specialty.builder()
                .name(dto.name)
                .build();
        Specialty parent = null;
        if (dto.parentId != null) {
            parent = specialtyRepository.findById(dto.parentId).orElseThrow(
                    () -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "parent not found"
                    )
            );
        }
        specialty.setParent(parent);
        specialtyRepository.save(specialty);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        var optionalSpecialty = specialtyRepository.findById(id);
        if (optionalSpecialty.isPresent()) {
            var specialty = optionalSpecialty.get();
            if (!specialtyRepository.existsByParent(specialty)) {
                specialtyRepository.delete(specialty);
            } else {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "service type has other service types as children"
                );
            }
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
    }
}
