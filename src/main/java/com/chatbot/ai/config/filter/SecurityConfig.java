package com.chatbot.ai.config.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desativa proteção CSRF (útil para APIs)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Libera tudo
                )
                .headers(headers -> headers.frameOptions(frame -> frame.disable())); // Permite acesso a H2 e etc

        return http.build();
    }
}
