package com.khedmatkar.demo.service.domain;

import com.khedmatkar.demo.account.entity.Specialist;
import com.khedmatkar.demo.exception.NoSuitableCandidateSpecialistFoundException;
import com.khedmatkar.demo.service.entity.ServiceRequest;
import org.springframework.stereotype.Component;

@Component
public interface SpecialistFinderStrategy {

    Specialist findSpecialist(ServiceRequest serviceRequest) throws NoSuitableCandidateSpecialistFoundException;
}
