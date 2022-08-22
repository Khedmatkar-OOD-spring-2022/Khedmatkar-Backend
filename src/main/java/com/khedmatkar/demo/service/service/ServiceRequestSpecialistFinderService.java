package com.khedmatkar.demo.service.service;

import com.khedmatkar.demo.account.entity.Customer;
import com.khedmatkar.demo.account.entity.Specialist;
import com.khedmatkar.demo.account.repository.SpecialistRepository;
import com.khedmatkar.demo.exception.NoSuitableCandidateSpecialistFoundException;
import com.khedmatkar.demo.exception.SpecialistNotFoundException;
import com.khedmatkar.demo.exception.SpecialtyHasChildSpecialtiesException;
import com.khedmatkar.demo.exception.SpecialtyNotFoundException;
import com.khedmatkar.demo.notification.service.AnnouncementMessage;
import com.khedmatkar.demo.notification.service.AnnouncementService;
import com.khedmatkar.demo.service.domain.SpecialistFinderFactory;
import com.khedmatkar.demo.service.dto.ServiceRequestCreationDTO;
import com.khedmatkar.demo.service.entity.ServiceRequest;
import com.khedmatkar.demo.service.entity.ServiceRequestSpecialist;
import com.khedmatkar.demo.service.entity.ServiceRequestSpecialistStatus;
import com.khedmatkar.demo.service.entity.ServiceRequestStatus;
import com.khedmatkar.demo.service.repository.ServiceRequestRepository;
import com.khedmatkar.demo.service.repository.ServiceRequestSpecialistRepository;
import com.khedmatkar.demo.service.repository.SpecialtyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
@Slf4j
public class ServiceRequestSpecialistFinderService {
    private final ServiceRequestRepository serviceRequestRepository;
    private final SpecialtyRepository specialtyRepository;
    private final SpecialistFinderFactory candidateFinderFactory;
    private final ServiceRequestSpecialistRepository serviceRequestSpecialistRepository;
    private final SpecialistRepository specialistRepository;
    private final AnnouncementService announcementService;
    private final ServiceRequestNoSpecialistCancellerService serviceRequestNoSpecialistCancellerService;

    public ServiceRequestSpecialistFinderService(ServiceRequestRepository serviceRequestRepository,
                                                 SpecialtyRepository specialtyRepository,
                                                 SpecialistFinderFactory candidateFinderFactory,
                                                 ServiceRequestSpecialistRepository serviceRequestSpecialistRepository,
                                                 SpecialistRepository specialistRepository,
                                                 AnnouncementService announcementService,
                                                 ServiceRequestNoSpecialistCancellerService serviceRequestNoSpecialistCancellerService) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.specialtyRepository = specialtyRepository;
        this.candidateFinderFactory = candidateFinderFactory;
        this.serviceRequestSpecialistRepository = serviceRequestSpecialistRepository;
        this.specialistRepository = specialistRepository;
        this.announcementService = announcementService;
        this.serviceRequestNoSpecialistCancellerService = serviceRequestNoSpecialistCancellerService;
    }

    @Transactional
    public void create(ServiceRequestCreationDTO dto, Customer customer) {
        var specialty = specialtyRepository.findById(dto.specialtyId)
                .orElseThrow(SpecialtyNotFoundException::new);

        if (specialtyRepository.existsByParent(specialty)) {
            throw new SpecialtyHasChildSpecialtiesException();
        }

        var serviceRequest = ServiceRequest.builder()
                .customer(customer)
                .specialty(specialty)
                .specialistHistory(new ArrayList<>())
                .description(dto.description)
                .address(dto.address)
                .status(ServiceRequestStatus.FINDING_SPECIALIST)
                .receptionDate(dto.receptionDate)
                .build();

        Specialist specialist;
        serviceRequestRepository.save(serviceRequest);

        if (dto.specialistId != null) {
            specialist = specialistRepository.findById(dto.specialistId)
                    .orElseThrow(SpecialistNotFoundException::new);
            setSpecialistForServiceRequest(serviceRequest, specialist);
        } else {
            findSpecialistForServiceRequest(serviceRequest);
        }

        announcementService.sendAnnouncementWithEmailToUser(
                customer,
                AnnouncementMessage.CUSTOMER_CREATES_SERVICE_REQUEST
        );
    }


    @Transactional
    public void findSpecialistForServiceRequest(ServiceRequest serviceRequest) {
        var specialistFinder = candidateFinderFactory.getSpecialistFinder();

        Specialist specialist;
        try {
            specialist = specialistFinder.findSpecialist(serviceRequest);
        } catch (NoSuitableCandidateSpecialistFoundException e) {
            serviceRequestNoSpecialistCancellerService.noSpecialistCancel(serviceRequest);
            return;
        }

        setSpecialistForServiceRequest(serviceRequest, specialist);
    }

    private void setSpecialistForServiceRequest(ServiceRequest serviceRequest, Specialist specialist) {
        var item = ServiceRequestSpecialist.builder()
                .status(ServiceRequestSpecialistStatus.WAITING_FOR_SPECIALIST_ACCEPTANCE)
                .specialist(specialist)
                .serviceRequest(serviceRequest)
                .build();

        serviceRequestSpecialistRepository.save(item);
        serviceRequest.setStatus(ServiceRequestStatus.WAITING_FOR_SPECIALIST_ACCEPTANCE);
        serviceRequestRepository.save(serviceRequest);

        announcementService.sendAnnouncementToUser(
                specialist,
                AnnouncementMessage.SPECIALIST_IS_CHOSEN_FOR_SERVICE_ANNOUNCEMENT,
                serviceRequest.getId()
        );
    }
}
