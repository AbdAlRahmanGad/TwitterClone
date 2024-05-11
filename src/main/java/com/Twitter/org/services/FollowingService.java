package com.Twitter.org.services;

import java.util.List;

import com.Twitter.org.Models.Response;

public interface FollowingService {

    List<String> findAllForUser(String username);
    Response addFollow(String username, String userToFollow);
    Response removeFollow(String username, String userToFollow);

    List<String> findAllFollowers(String username);
    boolean isFollowing(String username, String userToFollow);
}
