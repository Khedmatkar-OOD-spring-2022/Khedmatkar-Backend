package com.khedmatkar.demo.service.service;

import com.khedmatkar.demo.account.entity.Customer;
import com.khedmatkar.demo.service.dto.ServiceRequestCreationDTO;
import com.khedmatkar.demo.service.entity.ServiceRequest;
import com.khedmatkar.demo.service.entity.ServiceRequestSpecialist;
import com.khedmatkar.demo.service.entity.ServiceRequestSpecialistStatus;
import com.khedmatkar.demo.service.entity.ServiceRequestStatus;
import com.khedmatkar.demo.service.notdomain.CandidateSpecialistFinderStrategy;
import com.khedmatkar.demo.service.repository.ServiceRequestRepository;
import com.khedmatkar.demo.service.repository.ServiceRequestSpecialistRepository;
import com.khedmatkar.demo.service.repository.SpecialtyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

@Service
@Slf4j
public class ServiceRequestSpecialistFinderService {
    private final ServiceRequestRepository serviceRequestRepository;
    private final SpecialtyRepository specialtyRepository;
    private final CandidateSpecialistFinderStrategy candidateSpecialistFinderStrategy;
    private final ServiceRequestSpecialistRepository serviceRequestSpecialistRepository;

    public ServiceRequestSpecialistFinderService(ServiceRequestRepository serviceRequestRepository,
                                                 SpecialtyRepository specialtyRepository,
                                                 CandidateSpecialistFinderStrategy candidateSpecialistFinderStrategy,
                                                 ServiceRequestSpecialistRepository serviceRequestSpecialistRepository) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.specialtyRepository = specialtyRepository;
        this.candidateSpecialistFinderStrategy = candidateSpecialistFinderStrategy;
        this.serviceRequestSpecialistRepository = serviceRequestSpecialistRepository;
    }

    @Transactional
    public void create(ServiceRequestCreationDTO dto, Customer customer) {
        // todo: convert to a unique exception handling style

        var specialty = specialtyRepository.findById(dto.specialtyId)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.BAD_REQUEST, "service type has other service types as children"
                        )
                );

        var serviceRequest = ServiceRequest.builder()
                .customer(customer)
                .specialty(specialty)
                .description(dto.description)
                .address(dto.address)
                .status(ServiceRequestStatus.FINDING_SPECIALIST)
                .receptionDate(dto.receptionDate)
                .build();
        serviceRequestRepository.save(serviceRequest);

        findSpecialistForServiceRequest(serviceRequest);
    }


    @Transactional
    public void findSpecialistForServiceRequest(ServiceRequest serviceRequest) {
        var specialist = candidateSpecialistFinderStrategy.findSpecialist(serviceRequest);
        var item = ServiceRequestSpecialist.builder()
                .status(ServiceRequestSpecialistStatus.WAITING_FOR_SPECIALIST)
                .specialist(specialist)
                .serviceRequest(serviceRequest)
                .build();

        serviceRequestSpecialistRepository.save(item);
        serviceRequest.setStatus(ServiceRequestStatus.WAITING_FOR_SPECIALIST_ACCEPTANCE);
        serviceRequestRepository.save(serviceRequest);
    }
}
