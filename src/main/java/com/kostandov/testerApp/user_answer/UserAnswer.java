package com.kostandov.testerApp.user_answer;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@AllArgsConstructor
@Getter
@Table("user_answers")
public class UserAnswer {
    @Id
    private Long userAnswerId;
    private final long questionId;
    private final String answer;
    private final long userId;
    @Setter
    private AnswerStatus answerStatus;
}
