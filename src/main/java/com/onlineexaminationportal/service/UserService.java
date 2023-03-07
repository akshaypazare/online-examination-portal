package com.onlineexaminationportal.service;

import com.onlineexaminationportal.dto.UserDto;
import com.onlineexaminationportal.entity.User;

import java.util.List;

public interface UserService {

    //To register the user
    public User registerUser(UserDto userDto);

    //Get user by username
    public UserDto getUserByUserName(String username);

    User mapToEntity(UserDto userDto);//5-3-23

    UserDto mapToDto(User user);

    UserDto updateUser(UserDto userDto, String username);


    Integer deleteUser(String username);

    List<UserDto> getAllUsers();
}
