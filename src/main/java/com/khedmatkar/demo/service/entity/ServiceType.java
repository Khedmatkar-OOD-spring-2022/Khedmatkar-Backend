package com.khedmatkar.demo.service.entity;

import com.khedmatkar.demo.AbstractEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@Table(name = "service_types")
public class ServiceType extends AbstractEntity {

    private String name;

    @ManyToOne
    private ServiceType parent;
}
