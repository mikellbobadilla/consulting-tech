package com.mikellbobadilla.consultingtech.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/")
public class LoginController {

    @GetMapping(value = "login")
    public String getLogin(){
        return "login";
    }
}
