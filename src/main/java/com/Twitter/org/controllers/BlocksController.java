package com.Twitter.org.controllers;

import com.Twitter.org.Models.Response;
import com.Twitter.org.Models.Users.User;
import com.Twitter.org.mappers.Impl.UserMapper.UserResponseMapper;
import com.Twitter.org.services.Blocks.BlocksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Blocks", description = "Block operations")
@RestController
@RequestMapping("/blocks")
public class BlocksController {
    private final UserResponseMapper userResponseMapper;
    private final BlocksService blocksService;

    @Autowired
    public BlocksController(UserResponseMapper userResponseMapper, BlocksService blocksService) {
        this.userResponseMapper = userResponseMapper;
        this.blocksService = blocksService;
    }

    private static ResponseEntity<?> GetUserDetailsResponse(String userName) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String authUserName = userDetails.getUsername();
        boolean isAdmin = userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        // If the user is not authenticated or not an admin
        if (!authUserName.equals(userName) && !isAdmin) {
            return ResponseEntity.status(401).body("Unauthorized or Unauthenticated");
        }
        return null;
    }


    // User blocks another user
    @Operation(summary = "Block a user")
    @PostMapping("/{userName}/{blockedId}")
    public ResponseEntity<?> blockUser(@PathVariable String userName, @PathVariable String blockedId) {
        final ResponseEntity<?> Unauthorized_or_Unauthenticated = GetUserDetailsResponse(userName);
        if (Unauthorized_or_Unauthenticated != null) return Unauthorized_or_Unauthenticated;

        Response response = blocksService.blockUser(userName, blockedId);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    // User un-block another user
    @Operation(summary = "Unblock a user")
    @DeleteMapping("/{userName}/{blockedId}")
    public ResponseEntity<?> removeBlock(@PathVariable String userName, @PathVariable String blockedId) {
        final ResponseEntity<?> Unauthorized_or_Unauthenticated = GetUserDetailsResponse(userName);
        if (Unauthorized_or_Unauthenticated != null) return Unauthorized_or_Unauthenticated;

        Response response = blocksService.removeBlock(userName, blockedId);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }


    // Get list of blocked users by a user
    @Operation(summary = "Get blocked users")
    @GetMapping("/{userName}")
    public ResponseEntity<?> getBlockedUsers(@PathVariable String userName) {
        final ResponseEntity<?> Unauthorized_or_Unauthenticated = GetUserDetailsResponse(userName);
        if (Unauthorized_or_Unauthenticated != null) return Unauthorized_or_Unauthenticated;

        List<User> blockedUsers = blocksService.getBlockedUsers(userName);
        if (blockedUsers.isEmpty()) {
            return ResponseEntity.badRequest().body(new Response(false, "No blocked users"));
        }

        return ResponseEntity.ok(blockedUsers.stream().map(userResponseMapper::mapTo).collect(Collectors.toList()));
    }

    // Count number of blocked users by a user
    @Operation(summary = "Count blocked users")
    @GetMapping("/{userName}/count")
    public ResponseEntity<?> countBlockedUsers(@PathVariable String userName) {
        final ResponseEntity<?> Unauthorized_or_Unauthenticated = GetUserDetailsResponse(userName);
        if (Unauthorized_or_Unauthenticated != null) return Unauthorized_or_Unauthenticated;

        int count = blocksService.countBlockedUsers(userName);
        return ResponseEntity.ok(count);
    }

    // Check if a user blocked another user
    @Operation(summary = "Check if user is blocked")
    @GetMapping("/{userName}/{blockedId}")
    public ResponseEntity<?> isBlocked(@PathVariable String userName, @PathVariable String blockedId) {
        final ResponseEntity<?> Unauthorized_or_Unauthenticated = GetUserDetailsResponse(userName);
        if (Unauthorized_or_Unauthenticated != null) return Unauthorized_or_Unauthenticated;

        boolean isBlocked = blocksService.isBlocked(userName, blockedId);
        return ResponseEntity.ok(isBlocked);
    }
}
