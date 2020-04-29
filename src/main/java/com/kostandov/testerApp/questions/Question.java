package com.kostandov.testerApp.questions;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@Getter
@Table("questions")
@EqualsAndHashCode(of = "questionId")
public class Question {
    @Id
    private Long questionId;
    private final String text;
    private final String rightAnswer;
}
