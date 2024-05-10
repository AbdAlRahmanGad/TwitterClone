package com.Twitter.org.controllers;

import com.Twitter.org.Models.Response;
import com.Twitter.org.Models.Users.User;
import com.Twitter.org.Models.dto.UserDto;
import com.Twitter.org.mappers.Impl.UserMapper;
import com.Twitter.org.services.BlocksService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blocks")
public class BlocksController {
    private final UserMapper userMapper;
    private final BlocksService blocksService;

    public BlocksController(UserMapper userMapper, BlocksService blocksService) {
        this.userMapper = userMapper;
        this.blocksService = blocksService;
    }

    // User blocks another user
    @PostMapping("/{userName}/{blockedId}")
    public ResponseEntity<Response> blockUser(@PathVariable String userName, @PathVariable String blockedId) {
        Response response = blocksService.blockUser(userName, blockedId);
        if (response.isSuccess()) {
            UserDto userDto = (UserDto) response.getData();
            response.setData(userDto);
            return ResponseEntity.ok(response);
        }
        UserDto userDto = (UserDto) response.getData();
        response.setData(userDto);
        return ResponseEntity.badRequest().body(response);
    }

    // User un-block another user
    @DeleteMapping("/{userName}/{blockedId}")
    public ResponseEntity<Response> removeBlock(@PathVariable String userName, @PathVariable String blockedId) {
        Response response = blocksService.removeBlock(userName, blockedId);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    // Get list of blocked users by a user
    @GetMapping("/{userName}")
    public List<UserDto> getBlockedUsers(@PathVariable String userName) {
        List<User> users = blocksService.getBlockedUsers(userName);
        return users.stream().map(userMapper::mapTo).collect(Collectors.toList());
    }

    // Count number of blocked users by a user
    @GetMapping("/{userName}/count")
    public int countBlockedUsers(@PathVariable String userName) {
        return blocksService.countBlockedUsers(userName);
    }

    // Check if a user blocked another user
    @GetMapping("/{userName}/{blockedId}")
    public boolean isBlocked(@PathVariable String userName, @PathVariable String blockedId) {
        return blocksService.isBlocked(userName, blockedId);
    }
}
