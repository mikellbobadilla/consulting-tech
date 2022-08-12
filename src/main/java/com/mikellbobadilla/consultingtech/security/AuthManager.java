package com.mikellbobadilla.consultingtech.security;

import com.mikellbobadilla.consultingtech.service.LoginUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;


@Component
public class AuthManager implements AuthenticationManager{

/*

  @Autowired
  LoginUserService loginUserService;
  @Autowired
  PasswordEncoder encoder;
*/

/*  @Bean
  public AuthenticationManager authenticationManagerBean(){
    return super.authenticationManagerBean();
  }*/

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    return authentication;
  }
/*
  @Bean
  AuthenticationManagerBuilder authenticationManagerBuilder(AuthenticationManagerBuilder auth) throws Exception {
    return auth.userDetailsService(loginUserService).passwordEncoder(encoder).and();
  }*/

}
