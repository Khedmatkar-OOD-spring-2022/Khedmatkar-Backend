package com.khedmatkar.demo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .usernameParameter("email")
                .successHandler((request, response, authentication) -> {
                    //do nothing
                })
                .failureHandler((request, response, exception) -> {
                    HttpStatus httpStatus = HttpStatus.FORBIDDEN; // 403
                    Map<String, Object> data = new HashMap<>();
                    data.put("timestamp", new Date());
                    data.put("code", httpStatus.value());
                    data.put("status", httpStatus.name());
                    data.put("message", "username or password is incorrect.");

                    // setting the response HTTP status code
                    response.setStatus(httpStatus.value());
                    // Jackson JSON serializer instance
                    ObjectMapper objectMapper = new ObjectMapper();
                    // serializing the response body in JSON
                    response
                            .getOutputStream()
                            .println(
                                    objectMapper.writeValueAsString(data)
                            );
                })
                .and()
                .logout()
                .and()
                .csrf()
                .disable()
                .cors()
                .configurationSource(corsConfigurationSource());
        return http.build();
    }

    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}


@Component
class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        event.getSession().setMaxInactiveInterval(180 * 60);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
    }
}
