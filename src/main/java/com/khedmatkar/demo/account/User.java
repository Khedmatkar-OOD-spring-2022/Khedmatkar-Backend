package com.khedmatkar.demo.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;
    private String plainPassword;
    private String description;
}
