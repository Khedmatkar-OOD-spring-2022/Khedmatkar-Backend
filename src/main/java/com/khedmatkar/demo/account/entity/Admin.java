package com.khedmatkar.demo.account.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Table(name = "admins")
public class Admin extends User {

    @ElementCollection(targetClass = AdminPermission.class)
    @JoinColumn(name = "id")
    @Column(name = "permission", nullable = true)
    @Enumerated(EnumType.STRING)
    private Set<AdminPermission> permissions;

    public Set<String> getPermissionsFromString() {
        return this.getPermissions()
                .stream()
                .map(AdminPermission::name)
                .collect(Collectors.toSet());
    }

    public void setPermissionsFromString(Set<String> permissions) {
        this.setPermissions(permissions
                .stream()
                .map(AdminPermission::valueOf)
                .collect(Collectors.toSet()));
    }
}
