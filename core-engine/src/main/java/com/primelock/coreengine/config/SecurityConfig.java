package com.primelock.coreengine.config;

import com.primelock.coreengine.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {

        try{// 1. Disable CSRF (Cross-Site Request Forgery) because JWTs are immune to it
            http.csrf(AbstractHttpConfigurer::disable)
                    // 2. Configure endpoint access rules
                    .authorizeHttpRequests(auth -> auth.requestMatchers("/api/v1/auth/**").permitAll().anyRequest().authenticated())
                    // 3. Make the API completely stateless (no server-side memory sessions)
                    .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    // 4. Tell Spring to use the provider we built in ApplicationConfig
                    .authenticationProvider(authenticationProvider)
                    // 5. Put our custom JWT Bouncer directly in front of the standard Spring security filter
                    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
            return http.build();
        }catch (Exception e) {
            throw new RuntimeException("Failed to build SecurityFilterChain", e);
        }

    }
}
