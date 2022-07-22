package com.khedmatkar.demo.account.entity;

public enum UserType {
    ADMIN,
    CUSTOMER,
    SPECIALIST;

    public static class Role {
        public static final String ADMIN = "ROLE_ADMIN";
        public static final String CUSTOMER = "ROLE_CUSTOMER";
        public static final String SPECIALIST = "ROLE_SPECIALIST";
    }
}
