package com.primelock.coreengine.config;

import com.primelock.coreengine.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;

    // 1. Teaches Spring how to find users
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

    // 2. The core component that processes authentication requests
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        // Pass the userDetailsService directly into the constructor to satisfy the new Spring Security rules
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);

        // The password encoder still uses a setter
        authProvider.setPasswordEncoder(passwordEncoder);

        return authProvider;
    }

    // 3. The manager that coordinates the authentication process
    // FIX: Removed "throws Exception" from signature and handled it internally
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
        try {
            return config.getAuthenticationManager();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get AuthenticationManager", e);
        }
    }

    // 4. Defines the cryptographic algorithm we use to hash passwords
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}