package com.Twitter.org.services.Following;

import com.Twitter.org.Models.Response;
import com.Twitter.org.Models.Users.User;

import java.util.List;

public interface FollowingService {

    List<User> GetAllFollowing(String userName);

    List<User> GetAllFollowers(String userName);

    boolean isFollowing(String userName, String userToFollow);

    Response addFollower(String userName, String userToFollow);

    Response removeFollow(String userName, String userToFollow);

    Long countFollowers(String userName);

    Long countFollowing(String userName);
}
