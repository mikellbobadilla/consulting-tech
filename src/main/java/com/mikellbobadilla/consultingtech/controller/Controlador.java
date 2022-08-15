package com.mikellbobadilla.consultingtech.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mikellbobadilla.consultingtech.entity.AppUser;
import com.mikellbobadilla.consultingtech.repository.UserRepository;

@Controller
public class Controlador {

    @Autowired
    private UserRepository userRepository;

    @GetMapping({"/", "/index"})
    public String index(Model model){
        String saludo = "Hola Sting";
        model.addAttribute("saludo", saludo);
        return "index";
    }

    @GetMapping("/users")
    public String getUsers(Model model){
        List<AppUser> users = userRepository.findAll();

        model.addAttribute("users",users);

        return "views/users";
    }

}
