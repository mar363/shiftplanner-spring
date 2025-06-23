package com.demo.shiftplanner.shift_planner.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class AppConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .build();
        UserDetails user1 = User.withUsername("user1")
                .password(passwordEncoder.encode("user123"))
                .roles("EMPLOYEE")
                .build();
        return new InMemoryUserDetailsManager(admin, user1);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/employees").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/planning").hasRole("ADMIN")
                        .requestMatchers("/api/**").authenticated()
                )
                .httpBasic(httpBasic -> {
                });
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));
        return http.build();
    }
}
