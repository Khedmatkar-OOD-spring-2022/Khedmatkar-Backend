package com.khedmatkar.demo.account.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khedmatkar.demo.AbstractEntity;
import com.khedmatkar.demo.account.dto.UserDTO;
import com.khedmatkar.demo.account.dto.UserProfileDTO;
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
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public class User extends AbstractEntity {
    @Enumerated(EnumType.STRING)
    private UserType type;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    private String profilePicturePath;

    public void updateProfile(UserProfileDTO newUser) {
        this.email = newUser.email;
        this.firstName = newUser.firstName;
        this.lastName = newUser.lastName;
    }
}
