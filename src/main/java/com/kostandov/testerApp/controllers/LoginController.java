package com.kostandov.testerApp.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

   @PostMapping
    public String goHome(){
        return "redirect:/home";
    }
    @GetMapping
    public String loginPage(){
        return "login";
    }

}
