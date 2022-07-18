package com.khedmatkar.demo.service.entity;

import com.khedmatkar.demo.AbstractEntity;
import com.khedmatkar.demo.account.entity.Customer;
import com.khedmatkar.demo.account.entity.Specialist;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "service_requests")
@NoArgsConstructor
@Getter @Setter
@SuperBuilder(toBuilder = true)
public class ServiceRequest extends AbstractEntity {

    @ManyToOne
    private Specialty specialty;
    private String description;
    private String address;

    @OneToOne
    private Customer customer;

    @OneToOne
    private Specialist specialist;

    @OneToMany(mappedBy = "serviceRequest")
    private List<ServiceRequestSpecialist> specialistHistory;

    @Enumerated(EnumType.STRING)
    private ServiceRequestStatus status;
}
