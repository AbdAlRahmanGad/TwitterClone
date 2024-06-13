package com.Twitter.org.controllers;

import com.Twitter.org.Models.Users.User;
import com.Twitter.org.Models.dto.UserDto.UserResponseDto;
import com.Twitter.org.Models.dto.UserDto.UserUpdateDto;
import com.Twitter.org.mappers.Mapper;
import com.Twitter.org.services.User.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private final UserServiceImpl userService;
    private final Mapper<User, UserResponseDto> userResponseMapper;

    @Autowired
    public UserController(UserServiceImpl userService,
                          Mapper<User, UserResponseDto> userResponseMapper) {
        this.userService = userService;
        this.userResponseMapper = userResponseMapper;
    }

    // delete a user
    @DeleteMapping(path = "/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable("username") String username) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String authenticatedUsername = userDetails.getUsername();

        // Check if the authenticated user is the same as the user to be deleted or if the authenticated user is an admin
        if (username.equals(authenticatedUsername) || userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            userService.delete(username);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    // get a user
    @GetMapping(path = "/{username}")
    public ResponseEntity<?> getUser(@PathVariable("username") String username) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // if not authenticated, return 401
        if (userDetails == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Optional<User> foundUser = Optional.ofNullable(userService.findUserByUsername(username));
        return foundUser.map(user -> {
            UserResponseDto userResponseDto = userResponseMapper.mapTo(user);
            return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // update a user
    @PutMapping(path = "/{username}")
    public ResponseEntity<?> updateUser(@PathVariable("username") String username, @RequestBody UserUpdateDto userUpdateDto) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // if not authenticated, return 401
        if (userDetails == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // Check if the authenticated user is the same as the user to be updated or if the authenticated user is an admin
        if (username.equals(userDetails.getUsername()) || userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            Optional<User> foundUser = Optional.ofNullable(userService.findUserByUsername(username));
            if (foundUser.isPresent()) {
                User user = foundUser.get();
                user.updateFromDto(userUpdateDto);
                userService.save(user); // Assuming there is a save method in the service to update the user
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    // get all users
    @GetMapping(path = "/allUsers")
    public ResponseEntity<?> getAllUsers() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Check if the user is authenticated or not
        if (userDetails != null) {
            Iterable<User> users = userService.findAll();
            List<UserResponseDto> userResponseDtos = new ArrayList<>();
            users.forEach(user -> userResponseDtos.add(userResponseMapper.mapTo(user)));
            return userResponseDtos.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(userResponseDtos, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
