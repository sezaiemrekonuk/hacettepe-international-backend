package com.hacettepe.irbackend.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        logger.info("SecurityFilterChain is being created.");
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                            auth
                                    .requestMatchers("/api/v1/authenticate", "/api/v1/users/register").permitAll()
                                    .anyRequest().authenticated();
                            logger.info("Added /api/v1/authenticate and /api/v1/users/register to permitAll list.");
                        }
                )
                .exceptionHandling(exceptionHandling -> {
                            exceptionHandling
                                    .authenticationEntryPoint(jwtAuthenticationEntryPoint);
                            logger.info("Added jwtAuthenticationEntryPoint to exceptionHandling.");
                        }
                )
                .sessionManagement(sessionManagement -> {
                            sessionManagement
                                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                            logger.info("SessionCreationPolicy is set to STATELESS.");
                        }
                );

        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        logger.info("Added JwtRequestFilter before UsernamePasswordAuthenticationFilter.");

        return httpSecurity.build();
    }
}
