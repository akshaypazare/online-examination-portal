package com.onlineexaminationportal.controller;


import com.onlineexaminationportal.dto.JWTAuthResponse;
import com.onlineexaminationportal.dto.LoginDto;
import com.onlineexaminationportal.dto.UserDto;
import com.onlineexaminationportal.entity.User;
import com.onlineexaminationportal.repository.RoleRepository;
import com.onlineexaminationportal.repository.UserRepository;
import com.onlineexaminationportal.security.JwtTokenProvider;
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


//    @Autowired
//    private JwtTokenProvider tokenProvider;//20-1-23




//To Register new user or signup
    //localhost:8080/OEP/auth/signup

    @Autowired
    private JwtTokenProvider tokenProvider;    //to generate token for authentication


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

    //Get User by username
    //localhost:8080/OEP/auth/akshay23
    @GetMapping("/{username}") //5-3-23
    public ResponseEntity<UserDto> getUserByUserName(@PathVariable("username") String username) {
        //whenever you want to return a status code, the method return type should be ResponseEntity<>
        return ResponseEntity.ok(userService.getUserByUserName(username));
    }

//Get all user
    //localhost:8080/OEP/auth/GetAll
@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/GetAll")
    public List<UserDto> getAllUsers(){
        List<UserDto> userDtos = userService.getAllUsers();
        return userDtos;
    }

//update User using username
    //http://localhost:8080/OEP/auth/Update/akshay23
@PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/Update/{username}")
    public ResponseEntity<UserDto> updateUserByUserName(@RequestBody UserDto userDto, @PathVariable("username") String username) { //username is provided by url, userDto is supplied by JSON
        UserDto updatedUser = userService.updateUser(userDto, username);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);    //if you want to supply status and parameter, we have to create object
    }

//Deleting user using username
    //localhost:8080/OEP/auth/Delete/akshay23
@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/Delete/{username}")
    public ResponseEntity<String> deleteUserByUsername(@PathVariable("username") String username) {

        userService.deleteUser(username);
        return new ResponseEntity<>("User Deleted Successfully.", HttpStatus.OK);  //if we are retuning more than one parameter in the ResponseEntity then we will have to use new keyword

    }


//    7-3-23


    @PostMapping("/signin")   // for login
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword())
        );
        //we are creating the token and supplying the username and password in the form of token
//if the credentials are correct it will move further otherwise it will stop here and return the message Bad credentials to postman

        SecurityContextHolder.getContext ().setAuthentication(authentication);
        //it will set Authentication token

        //get token from tokenProvider
        String token = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTAuthResponse(token));
    }
}
