package com.Twitter.org.services.User;

import com.Twitter.org.Models.Users.User;

public interface UserService {

    User findUserByUsername(String username);
    Iterable<User> findAll();
    public User save(User user);
    void delete(String username);
}
