package com.khedmatkar.demo.account.entity;

import com.khedmatkar.demo.account.entity.User;
import com.khedmatkar.demo.service.entity.ServiceRequestSpecialist;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Table(name = "specialists")
public class Specialist extends User {

    @OneToMany(mappedBy = "specialist")
    private Set <SpecialtyCertificate> certificateSet;

    @OneToMany(mappedBy = "specialist")
    private Set<ServiceRequestSpecialist> relatedServiceRequests;
}
