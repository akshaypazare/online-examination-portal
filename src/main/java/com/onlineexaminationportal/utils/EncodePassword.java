package com.onlineexaminationportal.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EncodePassword { //created to encode the password in 64 encoding format

    public static void main(String[] args) {
        PasswordEncoder encodePassword = new BCryptPasswordEncoder();
    }
}
