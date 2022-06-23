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
@Table(name = "specialties")
public class Specialty extends AbstractEntity {

    private String name;

    @ManyToOne
    private Specialty parent;
}
