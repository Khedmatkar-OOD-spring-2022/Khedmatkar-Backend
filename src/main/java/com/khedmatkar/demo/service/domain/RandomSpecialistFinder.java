package com.khedmatkar.demo.service.domain;

import com.khedmatkar.demo.account.entity.Specialist;
import com.khedmatkar.demo.account.repository.SpecialistRepository;
import com.khedmatkar.demo.exception.NoSuitableCandidateSpecialistFoundException;
import com.khedmatkar.demo.service.entity.ServiceRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RandomSpecialistFinder implements SpecialistFinderStrategy {
    private final SpecialistRepository specialistRepository;

    public RandomSpecialistFinder(SpecialistRepository specialistRepository) {
        this.specialistRepository = specialistRepository;
    }

    @Override
    public Specialist findSpecialist(ServiceRequest serviceRequest) throws NoSuitableCandidateSpecialistFoundException {
        Optional<Specialist> randomSpecialist = specialistRepository.findRandomSpecialist();
        return randomSpecialist
                .orElseThrow(NoSuitableCandidateSpecialistFoundException::new);
    }
}
