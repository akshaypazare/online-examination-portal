package com.onlineexaminationportal.controller;

import com.onlineexaminationportal.entity.exam.Question;
import com.onlineexaminationportal.entity.exam.Quiz;
import com.onlineexaminationportal.service.QuestionService;
import com.onlineexaminationportal.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/Question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    QuizService quizService;

    @PostMapping("/addQuestion")
    public ResponseEntity<?> addQuestion(@RequestBody Question question){
        Question question1 = questionService.addQuestion(question);

        return new ResponseEntity<>(question1, HttpStatus.CREATED);
    }

    @PutMapping("/Update")
    public ResponseEntity<?>updateQuestion(@RequestBody Question question){
        Question updateQuestion = questionService.updateQuestion(question);

        return new ResponseEntity<>(updateQuestion, HttpStatus.ACCEPTED);
    }

//    @GetMapping("/GetAll")
//    public ResponseEntity<?> getQuestions(){
//        Set<Question> questions = questionService.getQuestions();
//
//        return new ResponseEntity<>(questions, HttpStatus.OK);
//
//    }

    @GetMapping("/Get/{questionId}")
    public ResponseEntity<?> getQuestion(@PathVariable("questionId") Long questionId){
        Question question = questionService.getQuestion(questionId);

        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    @GetMapping("/quiz/{qId}")
    public ResponseEntity<?> getQuestionsOfQuiz(@PathVariable("qId") Long qId){

//        Quiz quiz = new Quiz();
//        quiz.setQId(qId);
//
//        Set<Question> questionsOfQuiz = questionService.getQuestionsOfQuiz(quiz);
//
//        return new ResponseEntity<>(questionsOfQuiz, HttpStatus.OK);

        Quiz quiz = quizService.getQuiz(qId);
        Set<Question> questions = quiz.getQuestions();

        //Now we have set of all the question but we have to send the number of questions that they want to see (this is supplied in Entity class)

        List list = new ArrayList(questions);

        if(list.size()> Integer.parseInt(quiz.getNumberOfQuestions()))
        {
            list = list.subList(0, Integer.parseInt(quiz.getNumberOfQuestions()+1));
        }
        Collections.shuffle(list); // this will the list of questions that we are getting from the questions table
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @DeleteMapping("/Delete/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable("questionId") Long questionId){
        questionService.deleteQuestion(questionId);
        return new ResponseEntity<>("Question Deleted Successfully.", HttpStatus.OK);
    }




    //for Evaluating the quiz
    @PostMapping("/eval-quiz")
    public ResponseEntity<?> evalQuiz(@RequestBody List<Question> questions){
        System.out.println(questions);
        double marksGot = 0;
        int correctAnswer = 0;
        int attempted = 0;

        for (Question q : questions ) {
            //            System.out.println(q.getGivenAnswer());

            //single questions
            Question question = questionService.getQuestion(q.getQuesId());
            System.out.println(question);
            if(question.getAnswer().equals(q.getGivenAnswer())){
                correctAnswer++;

                double marksSingle = Double.parseDouble(questions.get(0).getQuiz().getMaxMarks())/questions.size();
                marksGot += marksSingle;
            }

            if(q.getGivenAnswer() != null){
                attempted++;
            }



        };
        System.out.println("marksGot");
        System.out.println(marksGot);
        System.out.println("correctAnswer");
        System.out.println(correctAnswer);
        System.out.println("attempted");
        System.out.println(attempted);

        return null;
    }
}
