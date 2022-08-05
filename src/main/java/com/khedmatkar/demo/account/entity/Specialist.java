package com.khedmatkar.demo.account.entity;

import com.khedmatkar.demo.service.entity.ServiceRequestSpecialist;
import com.khedmatkar.demo.service.entity.ServiceRequestSpecialistStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Table(name = "specialists")
public class Specialist extends User {

    @OneToMany(mappedBy = "specialist")
    private Set<Certificate> certificateSet;

    @OneToMany(mappedBy = "specialist")
    private Set<ServiceRequestSpecialist> relatedServiceRequests;

    public int getNumberOfOngoingServices() {
        return (int) relatedServiceRequests.stream()
                .map(ServiceRequestSpecialist::getStatus)
                .filter(x -> ServiceRequestSpecialistStatus.ONGOING_STATUSES.contains(x))
                .count();
    }
}
