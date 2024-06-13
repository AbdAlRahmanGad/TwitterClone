package com.Twitter.org.controllers;

import com.Twitter.org.Models.Response;
import com.Twitter.org.Models.Users.User;
import com.Twitter.org.Models.dto.UserDto.UserResponseDto;
import com.Twitter.org.mappers.Impl.UserMapper.UserResponseMapper;
import com.Twitter.org.services.Blocks.BlocksService;
import com.Twitter.org.services.Following.FollowingService;
import com.Twitter.org.services.Following.FollowingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FollowingController {

    private final FollowingService followingService;
    private final BlocksService blocksService;
    private final UserResponseMapper userResponseMapper;

    @Autowired
    public FollowingController(FollowingServiceImpl followingService, BlocksService blocksService, UserResponseMapper userResponseMapper) {
        this.followingService = followingService;
        this.blocksService = blocksService;
        this.userResponseMapper = userResponseMapper;
    }

    @GetMapping(path = "/{username}/following")
    public ResponseEntity<?> listFollowings(@PathVariable("username") String username) {
        // To get the following list of a user
        // -> the authenticated user must not be blocked by the user(username)

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            return ResponseEntity.badRequest()
                    .body(new Response(false, "User not authenticated"));
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String authenticatedUsername = userDetails.getUsername();

        if (blocksService.isBlocked(username, authenticatedUsername)) {
            return ResponseEntity.badRequest()
                    .body(new Response(false, "You are blocked by this user"));
        }

        List<User> followingUsers = followingService.GetAllFollowing(username);

        // Convert the list of users to a list of UserResponseDto
        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        for (User followingUser : followingUsers) {
            userResponseDtos.add(userResponseMapper.mapTo(followingUser));
        }

        return ResponseEntity.ok(userResponseDtos);
    }

    // Endpoint to list all followers of a user
    @GetMapping(path = "/{username}/followers")
    public ResponseEntity<?> listFollowers(@PathVariable("username") String username) {
        // To get the followers list of a user
        // 1. Must be Authenticated
        // 2. Must not be blocked by the user(username)

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            return ResponseEntity.badRequest()
                    .body(new Response(false, "User not authenticated"));

        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String authenticatedUsername = userDetails.getUsername();

        if (blocksService.isBlocked(username, authenticatedUsername)) {
            return ResponseEntity.badRequest()
                    .body(new Response(false, "You are blocked by this user"));
        }

        List<User> followers = followingService.GetAllFollowers(username);

        // Convert the list of users to a list of UserResponseDto
        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        for (User follower : followers) {
            userResponseDtos.add(userResponseMapper.mapTo(follower));
        }

        return ResponseEntity.ok(userResponseDtos);
    }

    // Endpoint to check if a user is following another user
    @GetMapping(path = "/{username}/isFollowing/{userToCheck}")
    public boolean isFollowing(@PathVariable("username") String username, @PathVariable("userToCheck") String userToCheck) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            return false;
        }
        return followingService.isFollowing(username, userToCheck);
    }

    @DeleteMapping(path = "/{username}/deleteFollowing/{userToRemoveFollowing}")
    public ResponseEntity<Response> deleteFollowing(@PathVariable("username") String username, @PathVariable("userToRemoveFollowing") String userToRemoveFollowing) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            return ResponseEntity.badRequest()
                    .body(new Response(false, "User not authenticated"));
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String authenticatedUsername = userDetails.getUsername();

        // The {username} must be the authenticated user
        if (!authenticatedUsername.equals(username)) {
            return ResponseEntity.badRequest()
                    .body(new Response(false, "You are not authorized to perform this action"));
        }

        Response response = followingService.removeFollow(username, userToRemoveFollowing);

        if (response.isSuccess()) {
            response.setData("success");
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().body(response);
    }

    // Follow a user
    @PostMapping(path = "/{username}/addFollowing/{userToAddFollowing}")
    public ResponseEntity<Response> createFollowing(@PathVariable("username") String username, @PathVariable("userToAddFollowing") String userToAddFollowing) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            return ResponseEntity.badRequest()
                    .body(new Response(false, "User not authenticated"));
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String authenticatedUsername = userDetails.getUsername();

        // The {username} must be the authenticated user
        if (!authenticatedUsername.equals(username)) {
            return ResponseEntity.badRequest()
                    .body(new Response(false, "You are not authorized to perform this action"));
        }

        Response response = followingService.addFollower(username, userToAddFollowing);

        if (response.isSuccess()) {
            response.setData("success");
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().body(response);
    }

    // Count the number of followers of a user
    @GetMapping(path = "/{username}/countFollowers")
    public ResponseEntity<Long> countFollowers(@PathVariable("username") String username) {
        return ResponseEntity.ok(followingService.countFollowers(username));
    }

    // Count the number of users a user is following
    @GetMapping(path = "/{username}/countFollowing")
    public ResponseEntity<Long> countFollowing(@PathVariable("username") String username) {
        return ResponseEntity.ok(followingService.countFollowing(username));
    }
}
