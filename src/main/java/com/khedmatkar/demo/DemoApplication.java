package com.khedmatkar.demo;

import com.khedmatkar.demo.account.entity.Admin;
import com.khedmatkar.demo.account.entity.AdminPermission;
import com.khedmatkar.demo.account.entity.UserType;
import com.khedmatkar.demo.account.repository.AdminRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import java.util.Set;

@SpringBootApplication
@SecurityScheme(name = "khedmatkar-api", scheme = "basic", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
@OpenAPIDefinition(info = @Info(title = "Khedmatkar API"))
public class DemoApplication {

    @Autowired
    private AdminRepository adminRepository;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    InitializingBean sendDatabase() {
        return () -> {
            if (adminRepository.findByEmail("root@gmail.com").isEmpty()) {
                adminRepository.save(
                        Admin.builder()
                                .email("root@gmail.com")
                                .firstName("root")
                                .lastName("root")
                                .password(PasswordEncoderFactories
                                        .createDelegatingPasswordEncoder()
                                        .encode("root")) // todo: save root password encrypted
                                .type(UserType.ADMIN)
                                .permissions(Set.of(AdminPermission.class.getEnumConstants()))
                                .build());
            }
        };
    }
}
