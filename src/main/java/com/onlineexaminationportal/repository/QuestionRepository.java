package com.onlineexaminationportal.repository;

import com.onlineexaminationportal.entity.exam.Question;
import com.onlineexaminationportal.entity.exam.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Set<Question> findByQuiz(Quiz quiz); //Quiz in findByQuiz should be the variable of the @ManyToOne mapping variable
}
