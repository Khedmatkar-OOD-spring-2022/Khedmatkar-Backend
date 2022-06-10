package com.khedmatkar.demo.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@Table(name = "service_types")
public class ServiceType {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private ServiceType parent;
}
