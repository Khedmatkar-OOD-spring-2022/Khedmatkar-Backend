package com.khedmatkar.demo.service.domain;

import com.khedmatkar.demo.account.entity.Specialist;
import com.khedmatkar.demo.account.entity.ValidationStatus;
import com.khedmatkar.demo.account.repository.SpecialistRepository;
import com.khedmatkar.demo.exception.NoSuitableCandidateSpecialistFoundException;
import com.khedmatkar.demo.service.entity.ServiceRequest;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomCertificatedSpecialistFinder implements SpecialistFinderStrategy {
    private final SpecialistRepository specialistRepository;

    public RandomCertificatedSpecialistFinder(SpecialistRepository specialistRepository) {
        this.specialistRepository = specialistRepository;
    }

    @Override
    public Specialist findSpecialist(ServiceRequest serviceRequest) throws NoSuitableCandidateSpecialistFoundException {
        var candidates = specialistRepository.findAllByCertificateSetSpecialtyAndCertificateSetStatus(
                serviceRequest.getSpecialty(),
                ValidationStatus.VALID
        );
        if (candidates.isEmpty()) {
            throw new NoSuitableCandidateSpecialistFoundException();
        }
        return candidates.get(new Random().nextInt(candidates.size()));
    }
}
