package com.khedmatkar.demo.account.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SpecialtyCertificateId implements Serializable {
    private Long id;
    private Specialist specialist;
}
