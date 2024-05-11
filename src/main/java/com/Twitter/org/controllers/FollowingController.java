package com.Twitter.org.controllers;

import com.Twitter.org.Models.Users.Following;
import com.Twitter.org.Models.Users.User;
import com.Twitter.org.Models.dto.FollowingDto;
import com.Twitter.org.Models.dto.UserDto;
import com.Twitter.org.mappers.Mapper;
import com.Twitter.org.services.Impl.FollowingServiceImpl;
import com.Twitter.org.services.Impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FollowingController {

    private final FollowingServiceImpl followingService;
    private final UserServiceImpl userService;
    private final Mapper<User, UserDto> userMapper;
    private Mapper<Following, FollowingDto> followingMapper;

    public FollowingController(FollowingServiceImpl followingService, UserServiceImpl userService, Mapper<Following, FollowingDto> followingMapper, Mapper<User, UserDto> userMapper) {
        this.followingService = followingService;
        this.userService = userService;
        this.followingMapper = followingMapper;
        this.userMapper = userMapper;
    }

    @GetMapping(path = "/{username}/following")
    public List<UserDto> listFollowings(@PathVariable("username") String username) {
        List<String> followingUsernames = followingService.findAllForUser(username);

        List<String[]> splitFollowingUsernames =
                followingUsernames.stream()
                        .map(username1 -> username1.split(","))
                        .toList();
        ArrayList<UserDto> followingUserDtos = new ArrayList<>();
        for (String[] splitFollowingUsername : splitFollowingUsernames) {

            followingUserDtos.add(new UserDto()
                    .builder()
                    .userName(splitFollowingUsername[0])
                    .firstName(splitFollowingUsername[1])
                    .lastName(splitFollowingUsername[2])
                    .bio(splitFollowingUsername[3])
                    .profilePic(splitFollowingUsername[4].getBytes())
                    .coverPic(splitFollowingUsername[5].getBytes())
                    .dateJoined(LocalDate.parse(splitFollowingUsername[6]))
                    .build());
        }

        return followingUserDtos;
    }

    // Endpoint to list all followers of a user
    @GetMapping(path = "/{username}/followers")
    public List<UserDto> listFollowers(@PathVariable("username") String username) {
        List<String> followersUsernames = followingService.findAllFollowers(username);

        List<String[]> splitFollowersUsernames =
                followersUsernames.stream()
                        .map(username1 -> username1.split(","))
                        .toList();
        ArrayList<UserDto> followersUserDtos = new ArrayList<>();
        for (String[] splitFollowersUsername : splitFollowersUsernames) {

            followersUserDtos.add(new UserDto()
                    .builder()
                    .userName(splitFollowersUsername[0])
                    .firstName(splitFollowersUsername[1])
                    .lastName(splitFollowersUsername[2])
                    .bio(splitFollowersUsername[3])
                    .profilePic(splitFollowersUsername[4].getBytes())
                    .coverPic(splitFollowersUsername[5].getBytes())
                    .dateJoined(LocalDate.parse(splitFollowersUsername[6]))
                    .build());
        }

        return followersUserDtos;
    }

    // Endpoint to check if a user is following another user
    @GetMapping(path = "/{username}/isFollowing/{userToCheck}")
    public boolean isFollowing(@PathVariable("username") String username, @PathVariable("userToCheck") String userToCheck) {
        return followingService.isFollowing(username, userToCheck);
    }

    // TODO(change return message)
    @DeleteMapping(path = "/{username}/deleteFollowing/{userToRemoveFollowing}")
    public ResponseEntity deleteFollowing(@PathVariable("username") String username, @PathVariable("userToRemoveFollowing") String userToRemoveFollowing) {
        followingService.removeFollow(username, userToRemoveFollowing);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    // TODO(change return message)
    @PostMapping(path = "/{username}/addFollowing/{userToAddFollowing}")
    public ResponseEntity createFollowing(@PathVariable("username") String username, @PathVariable("userToAddFollowing") String userToAddFollowing) {
        followingService.addFollow(username, userToAddFollowing);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
