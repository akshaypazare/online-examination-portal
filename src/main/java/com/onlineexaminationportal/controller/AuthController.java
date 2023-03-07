package com.onlineexaminationportal.controller;


import com.onlineexaminationportal.dto.LoginDto;
import com.onlineexaminationportal.dto.UserDto;
import com.onlineexaminationportal.entity.User;
import com.onlineexaminationportal.repository.RoleRepository;
import com.onlineexaminationportal.repository.UserRepository;
import com.onlineexaminationportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/OEP/auth")
public class AuthController {   //this controller will help us in registering a user and login


    @Autowired
    private UserService userService;  //to save the user details in DB


    @Autowired
    private UserRepository userRepository;   //created to implement the signup feature

    @Autowired
    private AuthenticationManager authenticationManager;  //7-3-23, to develop signIn feature
    //this is a builtIn class in spring security and this will help us to validate the username and password
    //now if we run the project the console will says that we should have to create a bean of this class, only @Autowired will not do the work
    //Now go to the SecurityConfig file and create the bean of AuthenticationManager by overriding the method authenticationManager() which is presnet in WebSecurityConfigurerAdapter






//To Register new user or signup
    //localhost:8080/OEP/auth/signup
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){   //hasErrors() will check for errors, if errors found then if {} will be executed
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // getFieldError() will catch the error from going to exception class
        // getDefaultMessage() will get the defaulte message provided by the validation annotations
        //for implementing validation we should have to Create object of BindingResult and change the generic of ResponseEntity to Object
        // and add an if condition when validation happens and the input is not correct then it will return some message to the postman


        // add check for username exists in a DB
        if (userRepository.existsByUsername(userDto.getUsername())) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }
// add check for email exists in DB
        if (userRepository.existsByEmail(userDto.getEmail())) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        userService.registerUser(userDto);
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }


//To SignIn
    //localhost:8080/OEP/auth/signin
    @PostMapping("/signin")   // for login
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword())
        );
        //we are creating the token and supplying the username and password
//if the credentials are correct it will move further otherwise it will stop here and return the message Bad credentials to postman

        SecurityContextHolder.getContext().setAuthentication(authentication);
        //it will set Authentication token
        //this token will go to the AuthenticationManagerBuilder to do further authentication

        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK );
    }
}
