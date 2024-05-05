package com.Twitter.org.controllers;

import java.util.ArrayList;
import java.util.List;

import com.Twitter.org.Models.Users.Following;
import com.Twitter.org.Models.Users.User;
import com.Twitter.org.Models.dto.FollowingDto;
import com.Twitter.org.Models.dto.UserDto;
import com.Twitter.org.mappers.Mapper;
import com.Twitter.org.services.Impl.FollowingServiceImpl;
import com.Twitter.org.services.Impl.UserServiceImpl;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
public class FollowingController {

    private final FollowingServiceImpl followingService;
    private final UserServiceImpl userService;
    private Mapper<Following, FollowingDto> followingMapper;

    private final Mapper<User, UserDto> userMapper;

    public FollowingController(FollowingServiceImpl followingService, UserServiceImpl userService, Mapper<Following, FollowingDto> followingMapper, Mapper<User, UserDto> userMapper) {
        this.followingService = followingService;
        this.userService = userService;
        this.followingMapper = followingMapper;
        this.userMapper = userMapper;
    }

    @GetMapping(path = "/{username}/following")
    public List<String> listFollowings(@PathVariable("username") String username) {
        List<String> followingUsernames = followingService.findAllForUser(username);
        return new ArrayList<>(followingUsernames);
    }
}
