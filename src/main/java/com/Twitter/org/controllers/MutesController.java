package com.Twitter.org.controllers;

import com.Twitter.org.Models.Response;
import com.Twitter.org.Models.Users.User;
import com.Twitter.org.Models.dto.UserDto;
import com.Twitter.org.mappers.Impl.UserMapper;
import com.Twitter.org.services.Mutes.MutesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mutes")
public class MutesController {
    private final MutesService mutesService;
    private final UserMapper userMapper;

    public MutesController(MutesService mutesService, UserMapper userMapper) {
        this.mutesService = mutesService;
        this.userMapper = userMapper;
    }

    @PostMapping("/{userName}/{mutedId}")
    public ResponseEntity<Response> muteUser(@PathVariable String userName, @PathVariable String mutedId) {
        Response response = mutesService.muteUser(userName, mutedId);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @DeleteMapping("/{userName}/{mutedId}")
    public ResponseEntity<Response> unMuteUser(@PathVariable String userName, @PathVariable String mutedId) {
        Response response = mutesService.unMuteUser(userName, mutedId);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @GetMapping("/{userName}")
    public List<UserDto> getMutedUsers(@PathVariable String userName) {
        List<User> users = mutesService.getMutedUsers(userName);
        return users.stream().map(userMapper::mapTo).collect(Collectors.toList());
    }

    @GetMapping("/{userName}/{mutedId}")
    public boolean isMuted(@PathVariable String userName, @PathVariable String mutedId) {
        return mutesService.isMuted(userName, mutedId);
    }

    @GetMapping("/{userName}/count")
    public int countMutedUsers(@PathVariable String userName) {
        return mutesService.countMutedUsers(userName);
    }

    @GetMapping("/{mutedId}/countMutedBy")
    public int countHasMutedByUsers(@PathVariable String mutedId) {
        return mutesService.countHasMutedByUsers(mutedId);
    }

    @GetMapping("/{mutedId}/users")
    public List<UserDto> getUsersWhoMutedUser(@PathVariable String mutedId) {
        List<User> users = mutesService.getUsersWhoMutedUser(mutedId);
        return users.stream().map(userMapper::mapTo).collect(Collectors.toList());
    }
}