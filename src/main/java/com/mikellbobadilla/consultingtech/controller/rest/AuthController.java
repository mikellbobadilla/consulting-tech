package com.mikellbobadilla.consultingtech.controller.rest;

import com.mikellbobadilla.consultingtech.entity.AppUser;
import com.mikellbobadilla.consultingtech.repository.UserRepository;
import com.mikellbobadilla.consultingtech.response.MessageResponse;
import com.mikellbobadilla.consultingtech.response.UserLogin;
import com.mikellbobadilla.consultingtech.service.LoginUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {

  @Autowired
  private AuthenticationManager authManager;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  LoginUserService userService;

  @PostMapping("/login")
  public ResponseEntity<MessageResponse>login(@RequestBody UserLogin userLogin){
    User newUser = (User) userService.loadUserByUsername(userLogin.getUsername());
    Authentication authentication = userService.authenticate(newUser, userLogin.getPassword());
    SecurityContextHolder.getContext().setAuthentication(authentication);
    return ResponseEntity.ok().body(new MessageResponse("The user was authenticate"));
  }

  @PostMapping("/register")
  public ResponseEntity<MessageResponse> register(@RequestBody AppUser userRequest){

    if(userRequest.getPassword().isEmpty()){
      return ResponseEntity.badRequest().body(
        new MessageResponse("The password is required!")
      );
    }

    if(userRepository.existsByEmail(userRequest.getEmail())){
      return ResponseEntity.badRequest().body(
              new MessageResponse(String.format("Error %s is already taken!", userRequest.getEmail()))
      );
    }

    if(userRepository.existsByUsername(userRequest.getUsername())){
      return ResponseEntity.badRequest().body(
              new MessageResponse(String.format("Username %s is already taken!", userRequest.getUsername()))
      );
    }
    
    AppUser user = new AppUser(
            userRequest.getName(),
            userRequest.getUsername(),
            userRequest.getEmail(),
            passwordEncoder.encode(userRequest.getPassword())
    );
    userRepository.save(user);
    return ResponseEntity.ok().body(new MessageResponse("User registered successfully"));
  }
}
