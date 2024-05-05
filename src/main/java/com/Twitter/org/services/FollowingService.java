package com.Twitter.org.services;

import java.util.List;

import com.Twitter.org.Models.Users.Following;

public interface FollowingService {

    List<String> findAllForUser(String username);
    void addFollower(String username, String userToFollow);
    void removeFollower(String username, String userToFollow);
}
