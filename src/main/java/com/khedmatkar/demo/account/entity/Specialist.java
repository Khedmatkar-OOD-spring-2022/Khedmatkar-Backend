package com.khedmatkar.demo.account.entity;

import com.khedmatkar.demo.service.entity.ServiceRequestSpecialist;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Table(name = "specialists")
public class Specialist extends User {

    @OneToMany(mappedBy = "specialist")
    private Set <Certificate> certificateSet;

    @OneToMany(mappedBy = "specialist")
    private Set<ServiceRequestSpecialist> relatedServiceRequests;
}
