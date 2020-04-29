package com.kostandov.testerApp.user_answer;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAnswerRepository extends CrudRepository<UserAnswer,Long> {
    @Query("select * from user_answers where user_id = :userId")
    List<UserAnswer> findByUserId(@Param("userId") Long userId);

    @Query("select * from user_answers where user_id = :userId and question_id = :questionId")
    Optional<UserAnswer> findByUserIdAndQuestionId(@Param("userId") long userId,@Param("questionId") long questionId);


    @Query("select count(distinct user_id) from user_answers")
    long countOfTestedUsers();

    @Query("select count(*) from user_answers ua where ua.user_id = :userId and answer_status = 'RIGHT' " )
    Float rightAnswersCount(@Param("userId") long userId);

    @Query("SELECT count(*) FROM( " +
            "    SELECT user_id FROM user_answers ua " +
            "    WHERE answer_status = 'RIGHT' " +
            "    GROUP BY user_id " +
            "    HAVING count(user_answer_id) >= " +
            "        (SELECT count(user_id) FROM user_answers WHERE user_id = :userId AND answer_status = 'RIGHT')) a")
    long countOfUsersWithMoreRightAnswers(@Param("userId") long userId);

    @Query("SELECT count(*) FROM( " +
            "    SELECT user_id FROM user_answers ua " +
            "    WHERE answer_status = 'RIGHT' " +
            "    GROUP BY user_id " +
            "    HAVING count(user_answer_id) < " +
            "        (SELECT count(user_id) FROM user_answers WHERE user_id = :userId AND answer_status = 'RIGHT')) a")
    long countOfUsersWithLessRightAnswers(@Param("userId") long userId);

    @Query("SELECT count(*) FROM user_answers WHERE user_id=:userId")
    Integer countOfAnsweredQuestions(@Param ("userId") long userId);
}
