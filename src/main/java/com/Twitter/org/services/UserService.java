package com.Twitter.org.services;

import com.Twitter.org.Models.Users.User;

public interface UserService {

    User findUserByUsername(String username);
    public User save(User user);

    void delete(String username);
}
