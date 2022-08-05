package com.khedmatkar.demo.account.entity;

import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

public enum AdminPermission implements GrantedAuthority {

    USER_LIST_RW("USER_LIST_RW"),
    USER_PROFILE_RW("USER_PROFILE_RW"),
    VALIDATE_CERTIFICATE_W("VALIDATE_CERTIFICATE_W"),
    SPECIALTY_W("SPECIALTY_W"),
    SERVICE_W("SERVICE_W"),
    QUESTIONNAIRE_RW("QUESTIONNAIRE_RW"),
    TECHNICAL_ISSUE_RW("TECHNICAL_ISSUE_RW"),
    FEEDBACK_RW("FEEDBACK_RW"),
    ROOT("ROOT");

    private final String permission;

    AdminPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public String getAuthority() {
        return permission;
    }

    public static class Role {
        public static final String USER_LIST_RW = "ROLE_USER_LIST_RW";
        public static final String USER_PROFILE_RW = "ROLE_USER_PROFILE_RW";
        public static final String VALIDATE_CERTIFICATE_W = "ROLE_VALIDATE_CERTIFICATE_W";
        public static final String SPECIALTY_W = "ROLE_SPECIALTY_W";
        public static final String SERVICE_W = "ROLE_SERVICE_W";
        public static final String QUESTIONNAIRE_RW = "ROLE_QUESTIONNAIRE_RW";
        public static final String TECHNICAL_ISSUE_RW = "ROLE_TECHNICAL_ISSUE_RW";
        public static final String FEEDBACK_RW = "ROLE_FEEDBACK_RW";
        public static final String ROOT = "ROLE_ROOT";
    }

    public boolean in (Set<AdminPermission> permissions) {
        return permissions.contains(this);
    }
}

