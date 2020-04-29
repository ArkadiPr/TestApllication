package com.kostandov.testerApp.controllers;

import com.kostandov.testerApp.user.User;
import com.kostandov.testerApp.user_answer.UserAnswerService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"","/","/home"})
public class HomePageController {

    private final  UserAnswerService userAnswerService;

    public HomePageController(UserAnswerService userAnswerService) {
        this.userAnswerService = userAnswerService;
    }

    @RequestMapping
    public String showHomePage(){
        return "home";
    }

    @PostMapping
    public String goToTest(){
        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userAnswerService.countOfAnsweredQuestions(user.getUserId())>0){
            return "redirect:/result";
        }
        else{
            return "redirect:/test";
        }
    }

}
