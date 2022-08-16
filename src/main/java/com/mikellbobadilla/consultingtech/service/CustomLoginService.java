package com.mikellbobadilla.consultingtech.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mikellbobadilla.consultingtech.security.AuthManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;

public class CustomLoginService extends UsernamePasswordAuthenticationFilter {

  private final Logger log = LoggerFactory.getLogger(CustomLoginService.class);

  @Autowired
  private AuthManager authManager;

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    log.info("Username is: {} password id: {}", username, password);
    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());

    return authManager.authenticate(authToken);
  }
}
