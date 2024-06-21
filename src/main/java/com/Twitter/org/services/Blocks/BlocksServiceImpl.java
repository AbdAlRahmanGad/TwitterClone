package com.Twitter.org.services.Blocks;

import com.Twitter.org.Models.Response;
import com.Twitter.org.Models.Users.Blocks.Blocks;
import com.Twitter.org.Models.Users.Blocks.BlocksId;
import com.Twitter.org.Models.Users.User;
import com.Twitter.org.Repository.BlocksRepository;
import com.Twitter.org.services.Following.FollowingService;
import com.Twitter.org.services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlocksServiceImpl implements BlocksService {
    private final BlocksRepository blocksRepository;
    private final UserService userService;
    private final FollowingService followService;

    @Autowired
    public BlocksServiceImpl(BlocksRepository blocksRepository, UserService userService, FollowingService followService) {
        this.blocksRepository = blocksRepository;
        this.userService = userService;
        this.followService = followService;
    }


    public Response validOperation(String username, String whomIBlocked) {
        if (username.equals(whomIBlocked)) {
            return new Response(false, "You cannot block yourself");
        }
        if (userService.findUserByUsername(username) == null) {
            return new Response(false, "User does not exist");
        }
        if (userService.findUserByUsername(whomIBlocked) == null) {
            return new Response(false, "Blocked user does not exist");
        }
        return new Response(true, "Valid operation", userService.findUserByUsername(username));
    }


    @Transactional
    @Override
    public Response blockUser(String userName, String whomIBlocked) {
        Response response = validOperation(userName, whomIBlocked);
        if (!response.isSuccess()) {
            return response;
        }
        if (blocksRepository.existsById(new BlocksId(userName, whomIBlocked))) {
            return new Response(false, "User already blocked");
        }
        User user = userService.findUserByUsername(userName);
        User blockedUser = userService.findUserByUsername(whomIBlocked);
        blocksRepository.save(new Blocks(user, blockedUser));

        // Remove follow if he is following me
        followService.removeFollow(userName, whomIBlocked);
        // Remove follow if I am following him
        followService.removeFollow(whomIBlocked, userName);

        return new Response(true, "User blocked successfully");
    }

    @Transactional
    @Override
    public Response removeBlock(String userName, String whomIBlocked) {
        Response response = validOperation(userName, whomIBlocked);
        if (!response.isSuccess()) {
            return response;
        }
        if (!blocksRepository.existsById(new BlocksId(userName, whomIBlocked))) {
            return new Response(false, "User not blocked");
        }

        blocksRepository.deleteById(new BlocksId(userName, whomIBlocked));
        return new Response(true, "User unblocked successfully");
    }

    @Override
    public List<User> getBlockedUsers(String userName) {
        User user = userService.findUserByUsername(userName);
        List<User> blockedUsers = new ArrayList<>();
        if (user != null) {
            List<Blocks> blocked = user.getBlockedUsers();
            blocked.forEach(block -> blockedUsers.add(block.getBlocked()));
        }
        return blockedUsers;
    }

    @Override
    public int countBlockedUsers(String userName) {
        User user = userService.findUserByUsername(userName);
        if (user == null) {
            return 0;
        } else {
            return user.getBlockedUsers().size();
        }
    }

    @Override
    public boolean isBlocked(String userName, String whomIBlocked) {
        return blocksRepository.existsById(new BlocksId(userName, whomIBlocked));
    }
}
