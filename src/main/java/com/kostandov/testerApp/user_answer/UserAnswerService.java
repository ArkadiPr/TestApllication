package com.kostandov.testerApp.user_answer;

import lombok.AllArgsConstructor;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserAnswerService {

    private final UserAnswerRepository userAnswerRepository;

    public Iterable<UserAnswer> findAll() {
        return userAnswerRepository.findAll();
    }

    public <S extends UserAnswer> S save(S s) {
        return userAnswerRepository.save(s);
    }

    public long count() {
        return userAnswerRepository.count();
    }

    public List<UserAnswer> findByUserId(Long userId){
        return userAnswerRepository.findByUserId(userId);
    }

    public Optional<UserAnswer> findByUserIdAndQuestionId(long userId, long questionId) {
        return userAnswerRepository.findByUserIdAndQuestionId(userId, questionId);
    }

    public long countOfTestedUsers() {
        return userAnswerRepository.countOfTestedUsers();
    }

    public float rightAnswersCount(long userId) {
        return userAnswerRepository.rightAnswersCount(userId);
    }

    public long countOfUsersWithMoreRightAnswers(long userId) {
        return userAnswerRepository.countOfUsersWithMoreRightAnswers(userId);
    }

    public long countOfUsersWithLessRightAnswers(long userId) {
        return userAnswerRepository.countOfUsersWithLessRightAnswers(userId);
    }


    public Integer countOfAnsweredQuestions(long userId) {
        return userAnswerRepository.countOfAnsweredQuestions(userId);
    }

    public void deleteById(Long aLong) {
        userAnswerRepository.deleteById(aLong);
    }
}
