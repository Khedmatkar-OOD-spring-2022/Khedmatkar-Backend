package com.khedmatkar.demo.service.controller;

import com.khedmatkar.demo.exception.ParentSpecialtyNotFoundException;
import com.khedmatkar.demo.exception.SpecialtyHasChildSpecialtiesException;
import com.khedmatkar.demo.exception.SpecialtyNotFoundException;
import com.khedmatkar.demo.service.dto.SpecialtyDTO;
import com.khedmatkar.demo.service.entity.Specialty;
import com.khedmatkar.demo.service.repository.SpecialtyRepository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/specialties")
public class SpecialtyRestController {
    private final SpecialtyRepository specialtyRepository;

    public SpecialtyRestController(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }


    @GetMapping("/{id}")
    public SpecialtyDTO getById(@PathVariable Long id) {
        var specialty = specialtyRepository.findById(id)
                .orElseThrow(SpecialtyNotFoundException::new);
        return SpecialtyDTO.from(specialty);
    }

    @GetMapping("")
    public List<SpecialtyDTO> getAll(
            @RequestParam(value = "onlyRoots", defaultValue = "false") boolean onlyRoots) {
        List <Specialty> specialties;
        if (onlyRoots) {
            specialties = specialtyRepository.findByParentId(null);
        } else {
            specialties = specialtyRepository.findAll();
        }
        return specialties.stream()
                .map(SpecialtyDTO::from)
                .collect(Collectors.toList());
    }

    @GetMapping("/search")
    public List<SpecialtyDTO> searchByName(@PathParam(value = "name") String name) {
        return specialtyRepository.findByNameStartsWith(name)
                .stream()
                .map(SpecialtyDTO::from)
                .collect(Collectors.toList());
    }

    @PostMapping
    public void post(@RequestBody @Valid SpecialtyDTO dto) {
        var specialty = Specialty.builder()
                .name(dto.name)
                .build();
        Specialty parent = null;
        if (dto.parentId != null) {
            parent = specialtyRepository.findById(dto.parentId)
                    .orElseThrow(ParentSpecialtyNotFoundException::new);
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
                throw new SpecialtyHasChildSpecialtiesException();
            }
        } else {
            throw new SpecialtyNotFoundException();
        }
    }

    @GetMapping("/{id}/sub-specialties")
    public List<SpecialtyDTO> getChildren(@PathVariable Long id) {
        return specialtyRepository.findByParentId(id)
                .stream()
                .map(SpecialtyDTO::from)
                .collect(Collectors.toList());
    }
}
