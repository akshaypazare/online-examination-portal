package com.onlineexaminationportal.repository;

import com.onlineexaminationportal.entity.exam.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
}
