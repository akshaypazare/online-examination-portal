package com.onlineexaminationportal.repository;

import com.onlineexaminationportal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //before we do any activity with the user table,
    // let say we are registering the data, before registering we need to check does the user exist or email exists or user and email exist

    //To check that we will built some custom methods

    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username, String email);
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);




    @Modifying  //for modification we can have only two return type either void or Integer
    @Transactional
    @Query("Delete From User where username = :username")
    Integer deleteByUsername(String username);
}
