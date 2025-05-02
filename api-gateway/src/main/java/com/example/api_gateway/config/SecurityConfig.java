package com.example.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http) {
        return http.authorizeExchange(authorizeExchangeSpec -> {
                    authorizeExchangeSpec.anyExchange().authenticated();
                })
                .httpBasic(withDefaults())
                .build();
    }

//    public SecurityFilterChain securityWebFilterChain(
//            HttpSecurity http) throws Exception {
//        return http.authorizeHttpRequests(auth ->{
//                    auth.anyRequest().authenticated();
//                })
//                .httpBasic(withDefaults())
//                .build();
//    }
}
