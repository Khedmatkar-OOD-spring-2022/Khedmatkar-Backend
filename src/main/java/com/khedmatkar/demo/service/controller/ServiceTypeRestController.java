package com.khedmatkar.demo.service.controller;

import com.khedmatkar.demo.service.dto.ServiceTypeDTO;
import com.khedmatkar.demo.service.entity.ServiceType;
import com.khedmatkar.demo.service.repository.ServiceTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/serviceTypes")
public class ServiceTypeRestController {

    private final ServiceTypeRepository serviceTypeRepository;

    public ServiceTypeRestController(ServiceTypeRepository serviceTypeRepository) {
        this.serviceTypeRepository = serviceTypeRepository;
    }


    @GetMapping("/{id}")
    public ServiceType getById(@PathVariable Long id) {
        var byId = serviceTypeRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "entity not found"
        );
    }

    @GetMapping("")
    public List<ServiceType> getAll() {
        return serviceTypeRepository.findAll();
    }

    @GetMapping("/search")
    public List<ServiceType> searchByName(@PathParam(value = "name") String prefix) {
        System.out.println(prefix);
        return serviceTypeRepository.findByNameStartsWith(prefix);
    }

    @PostMapping
    public void post(@RequestBody ServiceTypeDTO dto) {
        var serviceType = ServiceType.builder()
                .name(dto.name)
                .build();
        ServiceType parent = null;
        if (dto.parentId != null) {
            parent = serviceTypeRepository.findById(dto.parentId).orElseThrow(
                    () -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "parent not found"
                    )
            );
        }
        serviceType.setParent(parent);
        serviceTypeRepository.save(serviceType);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        var optionalServiceType = serviceTypeRepository.findById(id);
        if (optionalServiceType.isPresent()) {
            serviceTypeRepository.delete(optionalServiceType.get());
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
    }
}
