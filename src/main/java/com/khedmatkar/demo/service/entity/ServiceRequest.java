package com.khedmatkar.demo.service.entity;

import com.khedmatkar.demo.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

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
}
