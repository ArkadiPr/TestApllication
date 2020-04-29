package com.kostandov.testerApp.controllers;

import com.kostandov.testerApp.questions.Question;
import com.kostandov.testerApp.questions.QuestionService;
import com.kostandov.testerApp.user.User;
import com.kostandov.testerApp.user.UserService;
import com.kostandov.testerApp.user_answer.UserAnswer;
import com.kostandov.testerApp.user_answer.UserAnswerService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Controller
@RequestMapping("/result")
@AllArgsConstructor
public class ResultController {

    private final UserAnswerService userAnswerService;
    private final QuestionService questionService;
    private final UserService userService;

    @RequestMapping
    public String showResult(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<UserAnswer> usersAnswers = userAnswerService.findByUserId(user.getUserId());
        Iterable<Question> questions = questionService.findAll();

        Map<Long, Question> questionMap = StreamSupport.stream(questions.spliterator(), false)
                .collect(Collectors.toMap(Question::getQuestionId, Function.identity()));

        Map<Question, UserAnswer> questionsAndAnswers = usersAnswers.stream()
                .collect(Collectors.toMap(userAnswer -> questionMap.get(userAnswer.getQuestionId()), Function.identity()));



        long countOfTestedUsers = userAnswerService.countOfTestedUsers();
        model.addAttribute("UserName", user.getUsername());
        model.addAttribute("result", (
                BigDecimal
                        .valueOf(100)
                        .multiply(BigDecimal.valueOf(userAnswerService.rightAnswersCount(user.getUserId()))
                                .divide(BigDecimal.valueOf(questionService.count()), RoundingMode.HALF_UP))
                        .setScale(2, RoundingMode.HALF_UP).floatValue()));

        model.addAttribute("usersCount", userService.count());
        model.addAttribute("numberOfUsersBetter",
                BigDecimal
                        .valueOf(100)
                        .multiply(BigDecimal.valueOf(userAnswerService.countOfUsersWithMoreRightAnswers(user.getUserId())))
                        .divide(BigDecimal.valueOf(countOfTestedUsers), RoundingMode.HALF_UP)
                        .setScale(2, RoundingMode.HALF_UP)
                        .floatValue());
        model.addAttribute("numberOfUsersLess",
                BigDecimal
                        .valueOf(100)
                        .multiply(BigDecimal.valueOf(userAnswerService.countOfUsersWithLessRightAnswers(user.getUserId())))
                        .divide(BigDecimal.valueOf(countOfTestedUsers), RoundingMode.HALF_UP)
                        .setScale(2, RoundingMode.HALF_UP).floatValue());
        model.addAttribute("userTestedCount",countOfTestedUsers);

        model.addAttribute("userAnswersQuestions", questionsAndAnswers.entrySet());


        return "result";
    }

    @PostMapping
    public String goBack() {
        return "redirect:/home";
    }

}
