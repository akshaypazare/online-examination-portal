package com.onlineexaminationportal.controller;

import com.onlineexaminationportal.entity.exam.Quiz;
import com.onlineexaminationportal.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/Quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    //To add quizzes
    @PostMapping("/addQuiz")
    public ResponseEntity<Quiz> addQuiz(@RequestBody Quiz quiz){
        Quiz quiz1 = quizService.addQuiz(quiz);

        return new ResponseEntity<>(quiz1, HttpStatus.CREATED);
    }

    //To update Quiz
    @PutMapping("/Update")
    public ResponseEntity<?> updateQuiz(@RequestBody Quiz quiz){
        Quiz quiz1 = quizService.updateQuiz(quiz);
        return new ResponseEntity<>(quiz1, HttpStatus.ACCEPTED);
    }

    //To Get All quizzes
    @GetMapping("/GetAll")
    public ResponseEntity<?> getQuizzes(){
        Set<Quiz> quizzes = quizService.getQuizzes();
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }

    // To get a quiz
    @GetMapping("/Get/{quizId}")
    public ResponseEntity<?> getQuiz(@PathVariable("quizId") Long quizId){

        Quiz quiz = quizService.getQuiz(quizId);

        return new ResponseEntity<>(quiz, HttpStatus.OK);

    }

    @DeleteMapping("/Delete/{qId}")
    public ResponseEntity<String> deleteQuiz(@PathVariable("qId") Long qId){
        quizService.deleteQuiz(qId);
        return new ResponseEntity<>("Quiz Deleted Successfully.", HttpStatus.OK);
    }

}
