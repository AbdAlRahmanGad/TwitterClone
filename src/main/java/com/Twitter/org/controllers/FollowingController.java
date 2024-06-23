package com.Twitter.org.controllers;

import com.Twitter.org.Models.Response;
import com.Twitter.org.Models.Users.User;
import com.Twitter.org.Models.dto.UserDto.UserResponseDto;
import com.Twitter.org.mappers.Impl.UserMapper.UserResponseMapper;
import com.Twitter.org.services.Authentication.AuthenticationService;
import com.Twitter.org.services.Following.FollowingService;
import com.Twitter.org.services.Following.FollowingServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Tag(name = "Following", description = "Following operations")
public class FollowingController {

    private final FollowingService followingService;
    private final UserResponseMapper userResponseMapper;
    private final AuthenticationService authenticationService;

    @Autowired
    public FollowingController(FollowingServiceImpl followingService, UserResponseMapper userResponseMapper, AuthenticationService authenticationService) {
        this.followingService = followingService;
        this.userResponseMapper = userResponseMapper;
        this.authenticationService = authenticationService;
    }

    @Operation(summary = "List all users that a user is following")
    @GetMapping(path = "/{username}/following")
    public ResponseEntity<?> listFollowings(@PathVariable("username") String username) {
        final ResponseEntity<?> Unauthorized_or_Unauthenticated = authenticationService.sameUserOrAdminAuthenticated(username);
        if (Unauthorized_or_Unauthenticated.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return Unauthorized_or_Unauthenticated;
        }

        List<User> followingUsers = followingService.GetAllFollowing(username);

        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        for (User followingUser : followingUsers) {
            userResponseDtos.add(userResponseMapper.mapTo(followingUser));
        }

        return ResponseEntity.ok(userResponseDtos);
    }

    // Endpoint to list all followers of a user
    @Operation(summary = "List all followers of a user")
    @GetMapping(path = "/{username}/followers")
    public ResponseEntity<?> listFollowers(@PathVariable("username") String username) {
        final ResponseEntity<?> Unauthorized_or_Unauthenticated = authenticationService.sameUserOrAdminAuthenticated(username);
        if (Unauthorized_or_Unauthenticated.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return Unauthorized_or_Unauthenticated;
        }

        List<User> followers = followingService.GetAllFollowers(username);

        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        for (User follower : followers) {
            userResponseDtos.add(userResponseMapper.mapTo(follower));
        }

        return ResponseEntity.ok(userResponseDtos);
    }

    // Endpoint to check if a user is following another user
    @Operation(summary = "Check if a user is following another user")
    @GetMapping(path = "/{username}/isFollowing/{userToCheck}")
    public boolean isFollowing(@PathVariable("username") String username, @PathVariable("userToCheck") String userToCheck) {
        return followingService.isFollowing(username, userToCheck);
    }

    @Operation(summary = "Delete a user from following list")
    @DeleteMapping(path = "/{username}/deleteFollowing/{userToRemoveFollowing}")
    public ResponseEntity<?> deleteFollowing(@PathVariable("username") String username, @PathVariable("userToRemoveFollowing") String userToRemoveFollowing) {
        final ResponseEntity<?> Unauthorized_or_Unauthenticated = authenticationService.sameUserOrAdminAuthenticated(username);
        if (Unauthorized_or_Unauthenticated.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return Unauthorized_or_Unauthenticated;
        }

        Response response = followingService.removeFollow(username, userToRemoveFollowing);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().body(response);
    }

    // Follow a user
    @Operation(summary = "Add a user to following list")
    @PostMapping(path = "/{username}/addFollowing/{userToAddFollowing}")
    public ResponseEntity<?> createFollowing(@PathVariable("username") String username, @PathVariable("userToAddFollowing") String userToAddFollowing) {
        final ResponseEntity<?> Unauthorized_or_Unauthenticated = authenticationService.sameUserOrAdminAuthenticated(username);
        if (Unauthorized_or_Unauthenticated.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return Unauthorized_or_Unauthenticated;
        }

        Response response = followingService.addFollower(username, userToAddFollowing);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().body(response);
    }

    // Count the number of followers of a user
    @Operation(summary = "Count followers of a user")
    @GetMapping(path = "/{username}/countFollowers")
    public ResponseEntity<Long> countFollowers(@PathVariable("username") String username) {
        return ResponseEntity.ok(followingService.countFollowers(username));
    }

    // Count the number of users a user is following
    @Operation(summary = "Count following of a user")
    @GetMapping(path = "/{username}/countFollowing")
    public ResponseEntity<Long> countFollowing(@PathVariable("username") String username) {
        return ResponseEntity.ok(followingService.countFollowing(username));
    }
}
