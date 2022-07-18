package com.khedmatkar.demo.service.notdomain;

import com.khedmatkar.demo.account.entity.Specialist;
import com.khedmatkar.demo.account.repository.SpecialistRepository;
import com.khedmatkar.demo.service.entity.ServiceRequest;
import org.springframework.stereotype.Component;

@Component
public class RandomSpecialistFinder implements CandidateSpecialistFinderStrategy {
    private final SpecialistRepository specialistRepository;

    public RandomSpecialistFinder(SpecialistRepository specialistRepository) {
        this.specialistRepository = specialistRepository;
    }

    @Override
    public Specialist findSpecialist(ServiceRequest serviceRequest) {
        return specialistRepository.findRandomSpecialist()
                .get();
    }
}
