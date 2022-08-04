package com.khedmatkar.demo.account.dto;

import com.khedmatkar.demo.account.entity.User;
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

    public static UserProfileDTO from(User user) {
        return UserProfileDTO.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
}
