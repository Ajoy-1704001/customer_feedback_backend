package com.deb.customer_feedback_backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.deb.customer_feedback_backend.security.JwtAuthenticationEntryPoint;
import com.deb.customer_feedback_backend.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	private final JwtAuthenticationEntryPoint authenticationEntryPoint;
	private final JwtAuthenticationFilter authenticationFilter;
	
	public SecurityConfig(JwtAuthenticationEntryPoint authenticationEntryPoint, JwtAuthenticationFilter authenticationFilter) {
		this.authenticationEntryPoint = authenticationEntryPoint;
		this.authenticationFilter = authenticationFilter;
	}
	
	@Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults()).csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling((exceptionHandling) -> exceptionHandling.authenticationEntryPoint(authenticationEntryPoint))
                .sessionManagement((sm) -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        (authRequest) ->
                                authRequest.requestMatchers("/").permitAll()
                                        .requestMatchers("/api-docs",
                                                "/configuration/ui",
                                                "/api-docs/**",
                                                "/swagger-resources/**",
                                                "/configuration/security",
                                                "/swagger-ui/**",
                                                "/swagger-ui.html",
                                                "/webjars/**").permitAll()
                                        .requestMatchers("/account/register").permitAll()
                                        .requestMatchers("/account/login").permitAll()
                                        .requestMatchers("/account/verify").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/imgs/**").permitAll()
                                        .requestMatchers("/account/forget-password").permitAll()
                                        .requestMatchers("/account/forget-password/verify").permitAll()
                                        .requestMatchers("/account/reset-password").permitAll()
                                        .anyRequest().authenticated());
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
