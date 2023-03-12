package com.onlineexaminationportal.service.Impl;

import com.onlineexaminationportal.entity.exam.Quiz;
import com.onlineexaminationportal.repository.QuizRepository;
import com.onlineexaminationportal.service.QuizService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    QuizRepository quizRepository;

    private ModelMapper mapper;

    public QuizServiceImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Quiz addQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }


    @Override
    public Quiz updateQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @Override
    public Set<Quiz> getQuizzes() {
        return new LinkedHashSet<>(quizRepository.findAll());
    }

    @Override
    public Quiz getQuiz(Long quizId) {
        return quizRepository.findById(quizId).get();
    }

    @Override
    public void deleteQuiz(Long quizId) {

        quizRepository.deleteById(quizId);

    }

}
