package com.Twitter.org.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.Twitter.org.Models.Users.User;
import com.Twitter.org.Models.dto.UserDto;
import com.Twitter.org.mappers.Mapper;
import com.Twitter.org.services.User.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    private final UserServiceImpl userService;

    private final Mapper<User, UserDto> userMapper;

    public UserController(UserServiceImpl userService, Mapper<User, UserDto> userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    // create a new user
    @PostMapping(path = "/createUser")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        User user = userMapper.mapFrom(userDto);
        User savedUser = userService.save(user);
        return new ResponseEntity<>(userMapper.mapTo(savedUser), HttpStatus.CREATED);
    }

    // delete a user
    @DeleteMapping(path = "/{username}")
    public ResponseEntity deleteUser(@PathVariable("username") String username) {
        userService.delete(username);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // TODO on delete cascade for dependancies

    // get a user
    @GetMapping(path = "/{username}")
    public ResponseEntity<UserDto> getUser(@PathVariable("username") String username) {
        Optional<User> foundUser = Optional.ofNullable(userService.findUserByUsername(username));
        return foundUser.map(user -> {
            UserDto userDto = userMapper.mapTo(user);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/{username}")
    public ResponseEntity<UserDto> fullUpdateUser(
            @PathVariable("username") String username,
            @RequestBody UserDto userDto) {

        Optional<User> foundUser = Optional.ofNullable(userService.findUserByUsername(username));

        if(foundUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userDto.setUserName(username);
        User userEntity = userMapper.mapFrom(userDto);
        User savedUserEntity = userService.save(userEntity);
        return new ResponseEntity<>(
                userMapper.mapTo(savedUserEntity),
                HttpStatus.OK);
    }
    /// get all users
    @GetMapping(path = "/allUsers")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        Iterable<User> users = userService.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        users.forEach(user -> userDtos.add(userMapper.mapTo(user)));
        return userDtos.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

}
