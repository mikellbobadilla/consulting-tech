package com.mikellbobadilla.consultingtech.service;

import com.mikellbobadilla.consultingtech.entity.AppUser;
import com.mikellbobadilla.consultingtech.repository.UserRepository;
import com.mikellbobadilla.consultingtech.security.AuthManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LoginUserService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PasswordEncoder encoder;
  @Autowired
  AuthManager authManager;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AppUser user = userRepository.findByUsername(username).orElseThrow(
            ()-> new UsernameNotFoundException(String.format("user %s not found", username))
    );
    return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
  }

  public Boolean validateUser(String username){
    return userRepository.existsByUsername(username);
  }

  public Boolean validatePassword(String password, User user){
    return encoder.matches(password, user.getPassword());
  }

  public Authentication authenticate(User newUser, String password) throws BadCredentialsException {
    if(validateUser(newUser.getUsername()) && validatePassword(password, newUser)){
      return new UsernamePasswordAuthenticationToken(newUser.getUsername(), newUser.getPassword(), newUser.getAuthorities());
    }else{
      throw new BadCredentialsException("The credentials Failed username or password incorrect");
    }
  }
}
