package com.onlineexaminationportal.controller;

import com.onlineexaminationportal.dto.UserDto;
import com.onlineexaminationportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/OEP/User")
public class UserController {

    @Autowired
    private UserService userService;  //to save the user details in DB

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

}
