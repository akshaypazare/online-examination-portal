package com.onlineexaminationportal.entity.exam;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cId;

    private String title;

    private String description;

    @OneToMany(mappedBy = "category",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore   // this annotation is used to ignore the data of Quiz, when we try to get the data of category
    private Set<Quiz> quizzes = new LinkedHashSet<>();


}
