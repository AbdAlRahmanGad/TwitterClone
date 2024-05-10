package com.Twitter.org.services;

import com.Twitter.org.Models.Response;
import com.Twitter.org.Models.Users.User;

import java.util.List;

public interface BlocksService {
    // User blocks another user
    Response blockUser(String userName, String whomIBlocked);

    // User un-block another user
    Response removeBlock(String userName, String whomIBlocked);

    // Get list of blocked users by a user
    List<User> getBlockedUsers(String userName);

    // Count number of blocked users by a user
    int countBlockedUsers(String userName);

    // Check if a user blocked another user
    boolean isBlocked(String userName, String whomIBlocked);
}
