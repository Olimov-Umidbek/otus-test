package ru.otus.courses.java.advanced.registration.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import ru.otus.courses.java.advanced.registration.model.enums.UserRole;
import ru.otus.courses.java.advanced.registration.security.service.UserDetailsServiceImpl;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(exchange ->
                exchange
                    .pathMatchers("/users/**").hasAnyRole(UserRole.ADMIN.name())
                    .pathMatchers("/login").permitAll()
                    .anyExchange()
                    .authenticated()
            )
            .formLogin(spec ->
                spec.authenticationManager(authenticationManager())
            )
            .httpBasic(httpBasicSpec ->
                httpBasicSpec.authenticationManager(authenticationManager())
            )
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ReactiveAuthenticationManager authenticationManager() {
        return new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
    }
}