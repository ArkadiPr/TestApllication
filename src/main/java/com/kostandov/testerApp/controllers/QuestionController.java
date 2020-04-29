package com.kostandov.testerApp.controllers;

import com.kostandov.testerApp.questions.Question;
import com.kostandov.testerApp.questions.QuestionService;
import org.springframework.stereotype.Controller;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/textQuestions")
public class QuestionController {


    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    @Transactional
    public String addQuestion(@RequestParam String text, @RequestParam String rightAnswer, Model model) {
        Question question = new Question(null, text, rightAnswer.replaceAll("[\\s]{2,}", " ").toLowerCase());

        questionService.save(question);

        Iterable<Question> questionIterable = questionService.findAll();
        model.addAttribute("allquestions", questionIterable);

        return "textQuestions";
    }

    @GetMapping
    public String getAllQuestions(Model model) {
        Iterable<Question> questionIterable = questionService.findAll();
        model.addAttribute("allquestions", questionIterable);

        return "textQuestions";
    }
}
