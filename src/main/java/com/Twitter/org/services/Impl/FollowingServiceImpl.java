package com.Twitter.org.services.Impl;

import com.Twitter.org.Repository.FollowingRepository;
import com.Twitter.org.services.FollowingService;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void addFollow(String username, String userToFollow) {
        followingRepository.addFollower(username, userToFollow);
    }

    @Override
    public void removeFollow(String username, String userToFollow) {
        followingRepository.removeFollow(username, userToFollow);
    }

    @Override
    public List<String> findAllFollowers(String username) {
        return followingRepository.GetAllFollowers(username);
    }

    @Override
    public boolean isFollowing(String username, String userToFollow) {
        return followingRepository.isFollowing(username, userToFollow);
    }
}
