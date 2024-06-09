package com.Twitter.org.controllers;

import com.Twitter.org.Models.Users.User;
import com.Twitter.org.Models.dto.UserDto;
import com.Twitter.org.mappers.Impl.UserMapper;
import com.Twitter.org.services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public RegistrationController(PasswordEncoder passwordEncoder, UserService userService, UserMapper userMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.userMapper = userMapper;
    }


    // Register a new user
    @PostMapping("/register")
    public UserDto registerUser(@RequestBody UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = userMapper.mapFrom(userDto);
        User savedUser = userService.save(user);
        return userMapper.mapTo(savedUser);
    }
}
