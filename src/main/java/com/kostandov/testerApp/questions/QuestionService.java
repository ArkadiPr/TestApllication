package com.kostandov.testerApp.questions;

import lombok.AllArgsConstructor;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public <S extends Question> S save(S s) {
        return questionRepository.save(s);
    }

    public Iterable<Question> findAll() {
        return questionRepository.findAll();
    }

    public long count() {
        return questionRepository.count();
    }

    public Question loadById(Long id) {
        return questionRepository.findById(id).orElseThrow(()-> new RuntimeException());
    }

}
