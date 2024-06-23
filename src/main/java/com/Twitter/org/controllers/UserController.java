package com.Twitter.org.controllers;

import com.Twitter.org.Models.Users.User;
import com.Twitter.org.Models.dto.UserDto.UserResponseDto;
import com.Twitter.org.Models.dto.UserDto.UserUpdateDto;
import com.Twitter.org.mappers.Mapper;
import com.Twitter.org.services.Authentication.AuthenticationService;
import com.Twitter.org.services.User.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private final UserServiceImpl userService;
    private final Mapper<User, UserResponseDto> userResponseMapper;
    private final AuthenticationService authenticationService;

    @Autowired
    public UserController(UserServiceImpl userService,
                          Mapper<User, UserResponseDto> userResponseMapper, AuthenticationService authenticationService) {
        this.userService = userService;
        this.userResponseMapper = userResponseMapper;
        this.authenticationService = authenticationService;
    }

    // delete a user
    @DeleteMapping(path = "/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable("username") String username) {
        final ResponseEntity<?> Unauthorized_or_Unauthenticated = authenticationService.sameUserOrAdminAuthenticated(username);
        if (Unauthorized_or_Unauthenticated.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return Unauthorized_or_Unauthenticated;
        }

        userService.delete(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // get a user
    @GetMapping(path = "/{username}")
    public ResponseEntity<?> getUser(@PathVariable("username") String username) {
        final ResponseEntity<?> Unauthorized_or_Unauthenticated = authenticationService.sameUserOrAdminAuthenticated(username);
        if (Unauthorized_or_Unauthenticated.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return Unauthorized_or_Unauthenticated;
        }

        Optional<User> foundUser = Optional.ofNullable(userService.findUserByUsername(username));
        return foundUser.map(user -> {
            UserResponseDto userResponseDto = userResponseMapper.mapTo(user);
            return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // TODO: Refactor & Fix
    // update a user
    @PutMapping(path = "/{username}")
    public ResponseEntity<?> updateUser(@PathVariable("username") String username, @RequestBody UserUpdateDto userUpdateDto) {
        final ResponseEntity<?> Unauthorized_or_Unauthenticated = authenticationService.sameUserOrAdminAuthenticated(username);
        if (Unauthorized_or_Unauthenticated.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return Unauthorized_or_Unauthenticated;
        }

        Optional<User> foundUser = Optional.ofNullable(userService.findUserByUsername(username));
        if (foundUser.isPresent()) {
            User user = foundUser.get();
            user.updateFromDto(userUpdateDto);
            userService.save(user); // Assuming there is a save method in the service to update the user
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    // get all users
    @GetMapping(path = "/allUsers")
    public ResponseEntity<?> getAllUsers() {
        String username = authenticationService.getAuthenticatedUsername();

        // Check if the user is authenticated or not
        if (username != null) {
            Iterable<User> users = userService.findAll();
            List<UserResponseDto> userResponseDtos = new ArrayList<>();
            users.forEach(user -> userResponseDtos.add(userResponseMapper.mapTo(user)));
            return userResponseDtos.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(userResponseDtos, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
