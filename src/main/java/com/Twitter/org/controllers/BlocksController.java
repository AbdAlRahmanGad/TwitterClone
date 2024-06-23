package com.Twitter.org.controllers;

import com.Twitter.org.Models.Response;
import com.Twitter.org.Models.Users.User;
import com.Twitter.org.mappers.Impl.UserMapper.UserResponseMapper;
import com.Twitter.org.services.Authentication.AuthenticationService;
import com.Twitter.org.services.Blocks.BlocksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Blocks", description = "Block operations")
@RestController
@RequestMapping("/blocks")
public class BlocksController {
    private final UserResponseMapper userResponseMapper;
    private final BlocksService blocksService;
    private final AuthenticationService authenticationService;

    @Autowired
    public BlocksController(UserResponseMapper userResponseMapper, BlocksService blocksService, AuthenticationService authenticationService) {
        this.userResponseMapper = userResponseMapper;
        this.blocksService = blocksService;
        this.authenticationService = authenticationService;
    }

    // User blocks another user
    @Operation(summary = "Block a user")
    @PostMapping("/{username}/{blockedId}")
    public ResponseEntity<?> blockUser(@PathVariable String username, @PathVariable String blockedId) {
        final ResponseEntity<?> Unauthorized_or_Unauthenticated = authenticationService.sameUserOrAdminAuthenticated(username);
        if (Unauthorized_or_Unauthenticated.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return Unauthorized_or_Unauthenticated;
        }
        Response response = blocksService.blockUser(username, blockedId);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    // User un-block another user
    @Operation(summary = "Unblock a user")
    @DeleteMapping("/{username}/{blockedId}")
    public ResponseEntity<?> removeBlock(@PathVariable String username, @PathVariable String blockedId) {
        final ResponseEntity<?> Unauthorized_or_Unauthenticated = authenticationService.sameUserOrAdminAuthenticated(username);
        if (Unauthorized_or_Unauthenticated.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return Unauthorized_or_Unauthenticated;
        }

        Response response = blocksService.removeBlock(username, blockedId);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }


    // Get list of blocked users by a user
    @Operation(summary = "Get blocked users")
    @GetMapping("/{username}")
    public ResponseEntity<?> getBlockedUsers(@PathVariable String username) {
        final ResponseEntity<?> Unauthorized_or_Unauthenticated = authenticationService.sameUserOrAdminAuthenticated(username);
        if (Unauthorized_or_Unauthenticated.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return Unauthorized_or_Unauthenticated;
        }

        List<User> blockedUsers = blocksService.getBlockedUsers(username);
        if (blockedUsers.isEmpty()) {
            return ResponseEntity.badRequest().body(new Response(false, "No blocked users"));
        }

        return ResponseEntity.ok(blockedUsers.stream().map(userResponseMapper::mapTo).collect(Collectors.toList()));
    }

    // Count number of blocked users by a user
    @Operation(summary = "Count blocked users")
    @GetMapping("/{username}/count")
    public ResponseEntity<?> countBlockedUsers(@PathVariable String username) {
        final ResponseEntity<?> Unauthorized_or_Unauthenticated = authenticationService.sameUserOrAdminAuthenticated(username);
        if (Unauthorized_or_Unauthenticated.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return Unauthorized_or_Unauthenticated;
        }

        int count = blocksService.countBlockedUsers(username);
        return ResponseEntity.ok(count);
    }

    // Check if a user blocked another user
    @Operation(summary = "Check if user is blocked")
    @GetMapping("/{username}/{blockedId}")
    public ResponseEntity<?> isBlocked(@PathVariable String username, @PathVariable String blockedId) {
        final ResponseEntity<?> Unauthorized_or_Unauthenticated = authenticationService.sameUserOrAdminAuthenticated(username);
        if (Unauthorized_or_Unauthenticated.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return Unauthorized_or_Unauthenticated;
        }

        boolean isBlocked = blocksService.isBlocked(username, blockedId);
        return ResponseEntity.ok(isBlocked);
    }
}
