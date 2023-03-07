package com.onlineexaminationportal.dto;

import lombok.Data;

@Data
public class LoginDto {   //created for signIn
    private String usernameOrEmail;
    private String password;
}