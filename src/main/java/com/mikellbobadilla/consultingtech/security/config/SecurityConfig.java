package com.mikellbobadilla.consultingtech.security.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf().disable()
            .authorizeRequests().antMatchers("/api/auth/**").permitAll()
            .anyRequest().authenticated();
    return http.build();
  }

  @Bean
  public PasswordEncoder encoder(){
    return new BCryptPasswordEncoder(10);
  }

}
