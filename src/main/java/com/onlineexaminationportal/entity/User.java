package com.onlineexaminationportal.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Data
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
})
public class User {   //to implement the database authentication

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String name;
    private String username;
    private String password;
    @Version
    private int count;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    private long createdById;

    private long updatedById;

    private String token;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)  //we are applying many to many because many user can have many roles
    //when the user is fetch we want the role to be fetched at the same time that's why we are using FetchType.EAGER
    //and if we use FetchType.LAZY then user will be loaded but role will be loaded when required
    //cascade = CascadeType.ALL means when we make changes to one table another table will be affected accordingly

    //Now we will join the two tables which are user and role, to join two tables we use @JoinTable annotation
    // after joining two tables the third (mediator) table will be created
    @JoinTable(name="user_roles",
            joinColumns = @JoinColumn(name="user_id", referencedColumnName = "id"),
            //here we are joining the column of user table which is id (primary key) with mediator table which is user_id(foreign key)
            //when we use joinColumns which means we are going from first table User to second table mediator, see the diagram for better understanding

            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
            //here we are joining the column of role table which is id (primary key) with mediator table which is role_id(foreign key)
            //when we use inverseJoinColumns which means we are going from third table rolo to second table mediator, see the diagram for better understanding

    )


    private Set<Role> roles;
//    In order to many to many mapping, we need to think of a collection which means it can be List to List
//    but in the List data can be duplicate so we will use Set


}