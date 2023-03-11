package com.onlineexaminationportal.entity.exam;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quesId;

    @Column(length= 5000)
    private String content;

    private String image;

    private String option1;
    private String option2;
    private String option3;
    private String option4;

    @Column(length = 5000)
    private String answer;


    @ManyToOne(fetch = FetchType.EAGER)
    private Quiz quiz;
}
