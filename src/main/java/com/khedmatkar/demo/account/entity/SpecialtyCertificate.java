package com.khedmatkar.demo.account.entity;

import com.khedmatkar.demo.AbstractEntity;
import com.khedmatkar.demo.service.entity.Specialty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = "specialty_certificates")
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class SpecialtyCertificate extends AbstractEntity {

    @ManyToOne
    private Specialist specialist;

    @ManyToOne
    private Specialty specialty;

    @Setter
    @Column(nullable = false)
    @Builder.Default
    private Boolean validated = false;
}
