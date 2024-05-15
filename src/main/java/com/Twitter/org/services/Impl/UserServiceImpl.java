package com.Twitter.org.services.Impl;


import com.Twitter.org.Models.Users.User;
import com.Twitter.org.Repository.UserRepository;
import com.Twitter.org.services.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findById(username).orElse(null);
    }

    @Override
    public User save(User user) {
        // set DateJoined
        LocalDate date = LocalDate.now();
        user.setDateJoined(date);
        return userRepository.save(user);
    }

    @Override
    public void delete(String username) {
        userRepository.deleteById(username);
    }
}
