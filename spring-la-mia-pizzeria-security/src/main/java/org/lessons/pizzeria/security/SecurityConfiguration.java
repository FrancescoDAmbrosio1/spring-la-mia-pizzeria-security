package org.lessons.pizzeria.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

import org.lessons.pizzeria.service.DatabaseUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
        .requestMatchers("/pizze/create", "/pizze/edit/**", "/pizze/delete/**").hasAuthority("ADMIN")
        .requestMatchers("/ingredienti", "/ingredienti/**").hasAuthority("ADMIN")
        .requestMatchers("/offerte", "/offerte/**").hasAuthority("ADMIN")
        .requestMatchers("/pizze", "/pizze/**").hasAuthority("USER")
        .requestMatchers( "/pizze", "/pizze/**").hasAuthority("ADMIN")
//        .requestMatchers("/**").permitAll()
        )
    .formLogin(withDefaults()).logout(withDefaults());
    return http.build();
  }

  @Bean
  DatabaseUserDetailsService userDetailsService() {
    return new DatabaseUserDetailsService();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }
}
