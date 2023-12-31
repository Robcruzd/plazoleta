package com.pragma.plazoleta.infrastructue.security.config;

import com.pragma.plazoleta.infrastructue.security.jwt.JwtAuthEntryPoint;
import com.pragma.plazoleta.infrastructue.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final JwtAuthEntryPoint authEntryPoint;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.csrf().disable()
                .exceptionHandling().authenticationEntryPoint(authEntryPoint).and()
                .authorizeRequests()
                    .antMatchers("/swagger-ui/**", "/api/v1/**", "/api/v1/plazoleta/dish/list**", "/v3/**").permitAll()
//                    .antMatchers("/api/v1/plazoleta/restaurant/").hasAnyAuthority("Administrador")
//                    .antMatchers("/api/v1/plazoleta/dish/", "/api/v1/plazoleta/dish/enabledisable").hasAnyAuthority("Propietario")
//                    .antMatchers(HttpMethod.PUT,"/api/v1/plazoleta/order/").hasAnyAuthority("Empleado")
//                    .antMatchers("/api/v1/plazoleta/order/list**").hasAnyAuthority("Empleado")
//                    .antMatchers(HttpMethod.POST,"/api/v1/plazoleta/order/").hasAnyAuthority("Cliente")
                    .anyRequest().authenticated()
                .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}