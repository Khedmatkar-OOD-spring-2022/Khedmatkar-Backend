package com.khedmatkar.demo.account.dto;

import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@SuperBuilder
public class UserProfileDTO {

    @NotBlank
    public final String firstName;

    @NotBlank
    public final String lastName;

    @NotEmpty
    @Email
    public final String email;
}
