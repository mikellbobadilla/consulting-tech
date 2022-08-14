package com.mikellbobadilla.consultingtech.controller;

import com.mikellbobadilla.consultingtech.entity.AppUser;
import com.mikellbobadilla.consultingtech.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/users")
public class UserController {

  private final Logger log = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private UserRepository userRepository;

  @GetMapping("/all")
  public List<AppUser> getAll(){
    SecurityContext context = SecurityContextHolder.getContext();
    Authentication auth =  context.getAuthentication();

    log.info("The user {} passed to this controller", auth.getName());

    return userRepository.findAll();
  }
}
