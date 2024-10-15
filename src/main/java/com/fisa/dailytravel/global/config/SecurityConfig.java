package com.fisa.dailytravel.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private final JwtAuthConverter jwtAuthConverter;

    private final String[] WHITE_LIST = {"/public/**", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/actuator/**",};

    @Bean
    public SecurityFilterChain securityFilterChainLocal(HttpSecurity http) throws Exception {
        http.sessionManagement(session -> {
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }).formLogin(AbstractHttpConfigurer::disable).httpBasic(AbstractHttpConfigurer::disable).csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(req -> req.requestMatchers(WHITE_LIST).permitAll().anyRequest().authenticated()).oauth2ResourceServer(oauth2 -> {
            oauth2.jwt(jwt -> {
                jwt.jwtAuthenticationConverter(jwtAuthConverter);
            });
        }).addFilterBefore(new CustomJwtAuthenticationFilter(), org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
