package com.khedmatkar.demo.account;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserRegistrationDTO {
    public final String email;
    public final String plainPassword;
    public final String description;
}
