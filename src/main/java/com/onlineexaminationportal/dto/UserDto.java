package com.onlineexaminationportal.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class UserDto {

    private Long id;
    @Email(message = "Email format should be valid",
            regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;

    @NotBlank(message = "Name cannot be null and empty and size of string should be greater than 0")
    private String name;

    @NotBlank(message = "Username must not be null and must contain 1 or more characters")
    @Size(min = 6, max = 20,
    message = "username must be in between 6-20 characters")
<<<<<<< HEAD
    @Pattern(message = "username should have atmost 12 characters only",
            regexp = "^[a-zA-Z0-9]{8,12}$")
=======
    @Pattern(regexp = "^[a-zA-Z0-9]{8,12}$")
>>>>>>> 55d5e34dd4bd8668fe8858dac40eb4450abddf9e
    private String username;

    // The regex specifies that the password can contain characters from a to z, A to Z and 0-9 only,
    // also it must be in between 8 to 12 characters long.
    @Pattern(regexp = "^[a-zA-Z0-9]{8,12}$")
    private String password;
}