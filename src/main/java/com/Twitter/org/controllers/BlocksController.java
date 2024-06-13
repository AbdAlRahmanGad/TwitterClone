package com.Twitter.org.controllers;

import com.Twitter.org.Models.Response;
import com.Twitter.org.Models.Users.User;
import com.Twitter.org.Models.dto.UserDto.UserCreateDto;
import com.Twitter.org.Models.dto.UserDto.UserResponseDto;
import com.Twitter.org.mappers.Impl.UserMapper.UserResponseMapper;
import com.Twitter.org.services.Blocks.BlocksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    // User blocks another user
    @PostMapping("/{userName}/{blockedId}")
    public ResponseEntity<Response> blockUser(@PathVariable String userName, @PathVariable String blockedId) {
        Response response = blocksService.blockUser(userName, blockedId);
        if (response.isSuccess()) {
            UserCreateDto userCreateDto = (UserCreateDto) response.getData();
            response.setData(userCreateDto);
            return ResponseEntity.ok(response);
        }
        UserCreateDto userCreateDto = (UserCreateDto) response.getData();
        response.setData(userCreateDto);
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
    public List<UserResponseDto> getBlockedUsers(@PathVariable String userName) {
        List<User> users = blocksService.getBlockedUsers(userName);
        return users.stream().map(userResponseMapper::mapTo).collect(Collectors.toList());
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
