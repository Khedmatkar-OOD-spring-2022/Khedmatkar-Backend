package com.khedmatkar.demo.account.dto;

import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@AllArgsConstructor
@SuperBuilder
public class AdminPermissionDTO {

    @NotEmpty
    @Email
    public final String email;

    @NotNull
    public final Set<String> permissions;

}
