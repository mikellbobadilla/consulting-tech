package com.mikellbobadilla.consultingtech.controller;

import com.mikellbobadilla.consultingtech.entity.User;
import com.mikellbobadilla.consultingtech.repository.UserRepository;
import com.mikellbobadilla.consultingtech.response.MessageResponse;
import com.mikellbobadilla.consultingtech.response.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {

  @Autowired
  private AuthenticationManager authManager;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private UserRepository userRepository;


  @PostMapping("/login")
  public ResponseEntity<MessageResponse> login(@RequestBody UserLogin userLogin){
    // Process to authenticate
    Optional<User> user = userRepository.findByUsername(userLogin.getUsername());
    if(user.isPresent()){
      if(passwordEncoder.matches(userLogin.getPassword(), user.get().getPassword())){
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLogin.getUsername(), userLogin.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Suppose return a JWT token!! need to implement
        return ResponseEntity.ok().body(new MessageResponse("The user was authenticate!"));
      }else {
        return ResponseEntity.badRequest().body(
                new MessageResponse("username or password incorrect")
        );
      }

    }
    return ResponseEntity.notFound().build();
  }



  @PostMapping("/register")
  public ResponseEntity<MessageResponse> register(@RequestBody User userRequest){

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
    
    User user = new User(
            userRequest.getName(),
            userRequest.getUsername(),
            userRequest.getEmail(),
            passwordEncoder.encode(userRequest.getPassword())
    );
    userRepository.save(user);
    return ResponseEntity.ok().body(new MessageResponse("User registered successfully"));
  }
}
