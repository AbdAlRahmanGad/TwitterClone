package com.Twitter.org.services.Following;

import com.Twitter.org.Models.Response;
import com.Twitter.org.Models.Users.Following.Following;
import com.Twitter.org.Models.Users.Following.FollowingId;
import com.Twitter.org.Models.Users.User;
import com.Twitter.org.Repository.FollowingRepository;
import com.Twitter.org.Repository.UserRepository;
import com.Twitter.org.services.Authentication.AuthenticationService;
import com.Twitter.org.services.Blocks.BlocksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowingServiceImpl implements FollowingService {
    private final FollowingRepository followingRepository;
    private final UserRepository userRepository;
    private final BlocksService blocksService;
    private final AuthenticationService authenticationService;

    @Autowired
    public FollowingServiceImpl(FollowingRepository followingRepository, UserRepository userRepository, @Lazy BlocksService blocksService, AuthenticationService authenticationService) {
        this.followingRepository = followingRepository;
        this.userRepository = userRepository;
        this.blocksService = blocksService;
        this.authenticationService = authenticationService;
    }

    @Override
    public List<User> GetAllFollowing(String userName) {
        User user = userRepository.findById(userName).orElse(null);
        if (user != null) {
            String authenticatedUsername = authenticationService.getAuthenticatedUsername();
            if (authenticatedUsername == null || blocksService.isBlocked(userName, authenticatedUsername)) {
                return List.of();
            }

            return user.getFollowing().stream().map(Following::getFollowed).collect(Collectors.toList());
        }
        return List.of();
    }

    @Override
    public List<User> GetAllFollowers(String userName) {
        User user = userRepository.findById(userName).orElse(null);
        if (user != null) {
            String authenticatedUsername = authenticationService.getAuthenticatedUsername();
            if (authenticatedUsername == null || blocksService.isBlocked(userName, authenticatedUsername)) {
                return List.of();
            }

            return user.getFollowers().stream().map(Following::getFollower).collect(Collectors.toList());
        }
        return List.of();
    }

    @Override
    public boolean isFollowing(String userName, String userToFollow) {
        User user = userRepository.findById(userName).orElse(null);
        User followedUser = userRepository.findById(userToFollow).orElse(null);

        if (user != null && followedUser != null && user != followedUser && authenticationService.getAuthenticatedUsername() != null) {
            String username = user.getUserName();
            String followedUserUserName = followedUser.getUserName();
            return followingRepository.findById(new FollowingId(username, followedUserUserName)).isPresent();
        }
        return false;
    }

    @Transactional
    @Override
    public Response addFollower(String userName, String userToFollow) {
        User user = userRepository.findById(userName).orElse(null);
        User followedUser = userRepository.findById(userToFollow).orElse(null);

        // Check if already following
        if (isFollowing(userName, userToFollow)) {
            return new Response(false, "User already followed");
        }

        if (user != null && followedUser != null && !userName.equals(userToFollow)) {

            // Can't follow if blocked
            if (blocksService.isBlocked(userToFollow, userName)) {
                return new Response(false, "You are blocked by this user");
            }

            Following following = new Following(user, followedUser);
            followingRepository.save(following);
            return new Response(true, "User followed successfully");
        }
        return new Response(false, "Action failed");
    }

    @Override
    public Response removeFollow(String userName, String userToFollow) {
        User user = userRepository.findById(userName).orElse(null);
        User followedUser = userRepository.findById(userToFollow).orElse(null);
        if (user != null && followedUser != null && isFollowing(userName, userToFollow) && !userName.equals(userToFollow)) {
            followingRepository.delete(new Following(user, followedUser));
            return new Response(true, "User unfollowed successfully");
        }
        return new Response(false, "Action failed");
    }

    @Override
    public Long countFollowers(String userName) {
        User user = userRepository.findById(userName).orElse(null);
        if (user != null) {
            return (long) user.getFollowers().size();
        }
        return 0L;
    }

    @Override
    public Long countFollowing(String userName) {
        User user = userRepository.findById(userName).orElse(null);
        if (user != null) {
            return (long) user.getFollowing().size();
        }
        return 0L;
    }
}
