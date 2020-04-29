package com.kostandov.testerApp.controllers;

import com.kostandov.testerApp.questions.Question;
import com.kostandov.testerApp.questions.QuestionService;
import com.kostandov.testerApp.user.User;
import com.kostandov.testerApp.user_answer.AnswerStatus;
import com.kostandov.testerApp.user_answer.UserAnswer;
import com.kostandov.testerApp.user_answer.UserAnswerService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/test")
@AllArgsConstructor
public class TestController {

    private final QuestionService questionService;
    private final UserAnswerService userAnswerService;

    @GetMapping
    public String getAllQuestions(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<UserAnswer> usersAnswers = userAnswerService.findByUserId(user.getUserId());
        Map<Long, UserAnswer> questionIdUserAnswerMap = usersAnswers.stream()
                .collect(Collectors.toMap(UserAnswer::getQuestionId, Function.identity()));
        Iterable<Question> questions = questionService.findAll();

        Map<Question, String> questionsMap =
                StreamSupport
                        .stream(questions.spliterator(), false)
                        .collect(Collectors.toMap(Function.identity(), question -> {
                            UserAnswer userAnswer = questionIdUserAnswerMap.get(question.getQuestionId());
                            return userAnswer == null ? "" : userAnswer.getAnswer();
                        }));

        model.addAttribute("allQuestions", questionsMap.entrySet());

        return "test";
    }

    @PostMapping
    @Transactional
    public String answerTheTest(@RequestParam String answer, @RequestParam("questionID") Long questionId, @RequestParam String action) {

        if (action.equals("finishTest")) {
            return "redirect:/result";
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<UserAnswer> oldAnswer = userAnswerService.findByUserIdAndQuestionId(user.getUserId(), questionId);

        Question question = questionService.loadById(questionId);
        AnswerStatus answerStatus;
        if (answer.equalsIgnoreCase(question.getRightAnswer())) {
            answerStatus = AnswerStatus.RIGHT;
        } else {
            answerStatus = AnswerStatus.WRONG;
        }

        UserAnswer userAnswer = new UserAnswer(
                oldAnswer.map(UserAnswer::getUserAnswerId).orElse(null),
                questionId,
                answer,
                user.getUserId(),
                answerStatus
        );
        userAnswerService.save(userAnswer);

        return "redirect:/test";
    }

}
