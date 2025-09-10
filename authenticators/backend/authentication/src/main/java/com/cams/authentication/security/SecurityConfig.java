package com.cams.authentication.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Security configuration class to provide beans related to security.
 */
@Configuration
public class SecurityConfig {

    /**
     * Creates a BCryptPasswordEncoder bean. This is the recommended
     * password encoder for Spring Security, as it uses a strong hashing algorithm.
     *
     * @return A BCryptPasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
