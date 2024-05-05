package com.Twitter.org.services.Impl;

import java.util.List;
import java.util.Set;

import com.Twitter.org.Models.Users.Following;
import com.Twitter.org.Models.Users.User;
import com.Twitter.org.Repository.FollowingRepository;
import com.Twitter.org.services.FollowingService;
import org.springframework.stereotype.Service;

@Service
public class FollowingServiceImpl implements FollowingService {

    FollowingRepository followingRepository;

    public FollowingServiceImpl(FollowingRepository followingRepository) {
        this.followingRepository = followingRepository;
    }
    @Override
    public List<String> findAllForUser(String username) {
        return followingRepository.GetAllFollowing(username);
    }

    @Override
    public void addFollower(String username, String userToFollow) {
        followingRepository.addFollower(username, userToFollow);
    }

    @Override
    public void removeFollower(String username, String userToFollow) {
        followingRepository.removeFollower(username, userToFollow);
    }
}
