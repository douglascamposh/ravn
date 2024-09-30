package com.ravn.movies.config;

import com.ravn.movies.dto.enums.Role;
import com.ravn.movies.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

    private static final String[] WHITE_LIST_URLS = {
            //Swagger ui
            "/api-docs",
            "/swagger-ui/**",
            "/swagger-resources",
            "/swagger-resources/**",
            //public
            "/",
            "/api/v1/auth/*",
    };

    private static final String[] ADMIN_URLS = {
            "/api/v1/movies",
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests(request -> request
                        .antMatchers(WHITE_LIST_URLS).permitAll()
                        .antMatchers(HttpMethod.POST, ADMIN_URLS).hasRole(Role.ADMIN.toString())
                        .antMatchers(HttpMethod.PUT, ADMIN_URLS).hasRole(Role.ADMIN.toString())
                        .antMatchers(HttpMethod.DELETE, ADMIN_URLS).hasRole(Role.ADMIN.toString())
                        .anyRequest().authenticated()
                )
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
