package com.khedmatkar.demo.service.domain;

import com.khedmatkar.demo.AbstractEntity;
import com.khedmatkar.demo.account.entity.Specialist;
import com.khedmatkar.demo.account.entity.ValidationStatus;
import com.khedmatkar.demo.account.repository.SpecialistRepository;
import com.khedmatkar.demo.configuration.service.ConfigEntryService;
import com.khedmatkar.demo.exception.NoSuitableCandidateSpecialistFoundException;
import com.khedmatkar.demo.service.entity.ServiceRequest;
import com.khedmatkar.demo.service.entity.ServiceRequestSpecialist;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class NewCertificatedSpecialistFinder implements SpecialistFinderStrategy {
    private final SpecialistRepository specialistRepository;
    private final ConfigEntryService configEntryService;

    public NewCertificatedSpecialistFinder(
            SpecialistRepository specialistRepository,
            ConfigEntryService configEntryService) {
        this.specialistRepository = specialistRepository;
        this.configEntryService = configEntryService;
    }

    @Override
    public Specialist findSpecialist(ServiceRequest serviceRequest) throws NoSuitableCandidateSpecialistFoundException {
        var limit = Integer.parseInt(
                configEntryService.getValue(ConfigEntryService.CONCURRENT_ONGOING_SERVICES_LIMIT_KEY)
                        .orElseThrow()
                        .getValue());

        var previousSpecialistIds = serviceRequest.getSpecialistHistory()
                .stream()
                .map(ServiceRequestSpecialist::getSpecialist)
                .map(AbstractEntity::getId)
                .collect(Collectors.toList());

        var candidates =
                specialistRepository.findAllByCertificateSetSpecialtyAndCertificateSetStatus(
                        serviceRequest.getSpecialty(),
                        ValidationStatus.VALID
                )
                        .stream()
                        .filter(s -> !previousSpecialistIds.contains(s.getId()) )
                        .filter(m -> m.getNumberOfOngoingServices() < limit)
                        .collect(Collectors.toList());


        if (candidates.isEmpty()) {
            throw new NoSuitableCandidateSpecialistFoundException();
        }
        return candidates.get(
                new Random().nextInt(candidates.size())
        );
    }
}
