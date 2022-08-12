package com.mikellbobadilla.consultingtech.service;

import com.mikellbobadilla.consultingtech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LoginUserService implements UserDetailsService {

  private final UserRepository userRepository;

  @Autowired
  public LoginUserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    com.mikellbobadilla.consultingtech.entity.User user = userRepository.findByUsername(username).orElseThrow(
            ()-> new UsernameNotFoundException(String.format("user %s not found", username))
    );

    return new org.springframework.security.core.userdetails.User(
            user.getUsername(), user.getPassword(), new ArrayList<>()
    );
  }
}
