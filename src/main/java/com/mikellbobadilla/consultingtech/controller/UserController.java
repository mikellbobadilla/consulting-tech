package com.mikellbobadilla.consultingtech.controller;

import com.mikellbobadilla.consultingtech.entity.User;
import com.mikellbobadilla.consultingtech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/users")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @GetMapping("/all")
  public List<User> getAll(){

    return userRepository.findAll();
  }
}
