package com.kostandov.testerApp.controllers;

import com.kostandov.testerApp.user.User;
import com.kostandov.testerApp.user.UserService;
import com.kostandov.testerApp.user_answer.UserAnswer;
import com.kostandov.testerApp.user_answer.UserAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.Arrays;
import java.util.HashSet;

@Controller
public class UserRegistrationController {

    private final UserService userService;

    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    @Transactional
    public String addUser(@RequestParam String username,@RequestParam String password
            , Model model){
        User userFromDB=userService.findByUsername(username);
        if(userFromDB!=null) {
            model.addAttribute("message", "Пользователь суже существует");
            return "registration";
        }
        else{
            model.addAttribute("message", "");
        }
        User user=new User(null, username,password);

        userService.save(user);
        return "redirect:/login";
    }
}
