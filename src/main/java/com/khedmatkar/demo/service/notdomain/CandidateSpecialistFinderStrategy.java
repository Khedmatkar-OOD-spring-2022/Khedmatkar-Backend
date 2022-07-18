package com.khedmatkar.demo.service.notdomain;

import com.khedmatkar.demo.account.entity.Specialist;
import com.khedmatkar.demo.service.entity.ServiceRequest;
import org.springframework.stereotype.Component;

@Component
public interface CandidateSpecialistFinderStrategy {

    Specialist findSpecialist(ServiceRequest serviceRequest);
}
