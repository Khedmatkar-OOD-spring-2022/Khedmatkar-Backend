package com.khedmatkar.demo.account.service;

import com.khedmatkar.demo.account.entity.Admin;
import com.khedmatkar.demo.account.entity.UserType;
import com.khedmatkar.demo.account.repository.AdminRepository;
import com.khedmatkar.demo.account.repository.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.transaction.Transactional;
import java.util.Set;

@Configuration
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;

    CustomUserDetailsService(UserRepository userRepository,
                             AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.khedmatkar.demo.account.entity.User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("username not found"));

        Set<String> roles = new java.util.HashSet<>(Set.of(user.getType().toString(), "USER"));
        if (user.getType() == UserType.ADMIN) {
            roles.addAll(((Admin) user).getPermissionsFromString());
        }

        return User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(roles.toArray(String[]::new))
                .build();
    }
}
