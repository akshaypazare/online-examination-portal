package com.onlineexaminationportal.service.Impl;

import com.onlineexaminationportal.dto.UserDto;
import com.onlineexaminationportal.entity.Role;
import com.onlineexaminationportal.entity.User;
import com.onlineexaminationportal.repository.RoleRepository;
import com.onlineexaminationportal.repository.UserRepository;
import com.onlineexaminationportal.service.UserService;
import com.onlineexaminationportal.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;   //created to implement the signup feature

    @Autowired
    private RoleRepository roleRepository;  //to set the role for a new user

    private ModelMapper mapper;  //5-3-23, for implementing modelmapper concept
    //go to springboot application and write some configuration and create beans as well, because it's an external library not spring library

    public UserServiceImpl(ModelMapper mapper) {   //or @Autowired

        this.mapper = mapper;
        //we can add this by using generate constructor or we can add this attribute and this statement to do dependancy injection manually
    }

//    @Autowired
//    private PasswordEncoder passwordEncoder; // to encode the password which we are using for signup


//To Register a new user
    @Override
    public User registerUser(UserDto userDto) {

        // create user object
//        User user = new User();
//        user.setName(userDto.getName());
//        user.setUsername(userDto.getUsername());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());      //(passwordEncoder.encode(UserDto.getPassword()));

        User user = mapToEntity(userDto);

        //we need to set the role for new user, whether the user will be USER or ADMIN etc.
        Role roles = roleRepository.findByName("ROLE_ADMIN").get();   //we assigning this user as ADMIN, it will find that record and put that into and object
        user.setRoles(Collections.singleton(roles)); //now convert that object into set
        //Collections class has the singleton() method which will return the immutable set containing specified object

        userRepository.save(user);

        return user;
    }

    //Get user by username
    @Override
    public UserDto getUserByUserName(String username) {

        User user = userRepository.findByUsername(username).orElseThrow(                   //if record is not found then orElseThrow will work
                () -> new ResourceNotFoundException("user", "username", username)     // Using lambdas expression, we are creating object and calling the ResourceNotFoundException method

        );

        //here the return type will be Optional so we are conveting this to user by using get();

        UserDto userDto = mapToDto(user);//converting entity to dto for security

        return userDto;
    }

    @Override
    public User mapToEntity(UserDto userDto) {
        User user = mapper.map(userDto, User.class);
        return user;
    }

    @Override
    public UserDto mapToDto(User user) {
        UserDto userDto = mapper.map(user, UserDto.class);
        return userDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String username) {


        User user = userRepository.findByUsername(username).orElseThrow(
                //firstly check the username is there or not
                //now we are getting the data from the database which has to be updated in post variable
                () -> new ResourceNotFoundException("User", "username", username)
        );

        //now we are getting the data from the database which has to be updated in user variable



        //now map that userDto data that we are getting from Postman to Entity which is fetched by findByusername
            user.setEmail(userDto.getEmail());
            user.setName(userDto.getName());
            user.setUsername(userDto.getUsername());
            user.setPassword(userDto.getPassword());



        User updatedUser = userRepository.save(user); //save the updated data

        return mapToDto(updatedUser); //Convert the Entity to Dto and return back to controller

    }

    @Override
    public Integer deleteUser(String username) {
        return userRepository.deleteByUsername(username);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(user -> mapToDto(user)).collect(Collectors.toList());
    }

}
