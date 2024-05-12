package com.Twitter.org.services;

import java.util.List;

import com.Twitter.org.Models.Users.Following;

public interface FollowingService {

    List<String> findAllForUser(String username);
    void addFollow(String username, String userToFollow);
    void removeFollow(String username, String userToFollow);

    List<String> findAllFollowers(String username);
    boolean isFollowing(String username, String userToFollow);
}
