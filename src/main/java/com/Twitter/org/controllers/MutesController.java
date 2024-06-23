package com.Twitter.org.controllers;

import com.Twitter.org.Models.Response;
import com.Twitter.org.Models.Users.User;
import com.Twitter.org.mappers.Impl.UserMapper.UserResponseMapper;
import com.Twitter.org.services.Authentication.AuthenticationService;
import com.Twitter.org.services.Mutes.MutesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Mutes", description = "Mutes Operations")
@RestController
@RequestMapping("/mutes")
public class MutesController {
    private final MutesService mutesService;
    private final UserResponseMapper userMapper;
    private final AuthenticationService authenticationService;

    public MutesController(MutesService mutesService, UserResponseMapper userMapper, AuthenticationService authenticationService) {
        this.mutesService = mutesService;
        this.userMapper = userMapper;
        this.authenticationService = authenticationService;
    }

    @Operation(summary = "Mute User")
    @PostMapping("/{username}/{mutedId}")
    public ResponseEntity<?> muteUser(@PathVariable String username, @PathVariable String mutedId) {
        final ResponseEntity<?> Unauthorized_or_Unauthenticated = authenticationService.sameUserOrAdminAuthenticated(username);
        if (Unauthorized_or_Unauthenticated.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return Unauthorized_or_Unauthenticated;
        }

        Response response = mutesService.muteUser(username, mutedId);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @Operation(summary = "Unmute User")
    @DeleteMapping("/{username}/{mutedId}")
    public ResponseEntity<?> unMuteUser(@PathVariable String username, @PathVariable String mutedId) {
        final ResponseEntity<?> Unauthorized_or_Unauthenticated = authenticationService.sameUserOrAdminAuthenticated(username);
        if (Unauthorized_or_Unauthenticated.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return Unauthorized_or_Unauthenticated;
        }

        Response response = mutesService.unMuteUser(username, mutedId);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @Operation(summary = "Get Muted Users")
    @GetMapping("/{username}")
    public ResponseEntity<?> getMutedUsers(@PathVariable String username) {
        final ResponseEntity<?> Unauthorized_or_Unauthenticated = authenticationService.sameUserOrAdminAuthenticated(username);
        if (Unauthorized_or_Unauthenticated.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return Unauthorized_or_Unauthenticated;
        }

        List<User> users = mutesService.getMutedUsers(username);
        return new ResponseEntity<>(users.stream().map(userMapper::mapTo).collect(Collectors.toList()), HttpStatus.OK);
    }

    @Operation(summary = "Check if User is Muted")
    @GetMapping("/{username}/{mutedId}")
    public ResponseEntity<?> isMuted(@PathVariable String username, @PathVariable String mutedId) {
        final ResponseEntity<?> Unauthorized_or_Unauthenticated = authenticationService.sameUserOrAdminAuthenticated(username);
        if (Unauthorized_or_Unauthenticated.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return Unauthorized_or_Unauthenticated;
        }

        boolean isMuted = mutesService.isMuted(username, mutedId);
        return ResponseEntity.ok(isMuted);
    }

    @Operation(summary = "Count Muted Users")
    @GetMapping("/{username}/count")
    public ResponseEntity<?> countMutedUsers(@PathVariable String username) {
        final ResponseEntity<?> Unauthorized_or_Unauthenticated = authenticationService.sameUserOrAdminAuthenticated(username);
        if (Unauthorized_or_Unauthenticated.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return Unauthorized_or_Unauthenticated;
        }

        Integer count = mutesService.countMutedUsers(username);
        return ResponseEntity.ok(count);
    }

    // TODO: no need for this endpoint
    // Should only be accessible by an admin
    @Operation(summary = "Count number of users who muted a user")
    @GetMapping("/{mutedId}/countMutedBy")
    public ResponseEntity<?> countHasMutedByUsers(@PathVariable String mutedId) {
        final boolean isAdmin = authenticationService.AdminAuthenticated();
        if (!isAdmin) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized or Unauthenticated");
        }

        Integer count = mutesService.countHasMutedByUsers(mutedId);
        return ResponseEntity.ok(count);
    }

    // TODO: no need for this endpoint
    // Should only be accessible by an admin
    @Operation(summary = "Return a list of users who muted a user")
    @GetMapping("/{mutedId}/users")
    public ResponseEntity<?> getUsersWhoMutedUser(@PathVariable String mutedId) {
        final boolean isAdmin = authenticationService.AdminAuthenticated();
        if (!isAdmin) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized or Unauthenticated");
        }

        List<User> users = mutesService.getUsersWhoMutedUser(mutedId);
        return new ResponseEntity<>(users.stream().map(userMapper::mapTo).collect(Collectors.toList()), HttpStatus.OK);
    }
}