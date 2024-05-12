package com.Twitter.org.services.Impl;

import com.Twitter.org.Models.Response;
import com.Twitter.org.Models.Users.Mutes.Mutes;
import com.Twitter.org.Models.Users.Mutes.MutesId;
import com.Twitter.org.Models.Users.User;
import com.Twitter.org.Repository.MutesRepository;
import com.Twitter.org.services.MutesService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MutesServiceImpl implements MutesService {
    private final MutesRepository mutesRepository;
    private final UserServiceImpl userService;

    public MutesServiceImpl(MutesRepository mutesRepository, UserServiceImpl userService) {
        this.mutesRepository = mutesRepository;
        this.userService = userService;
    }

    public Response validOperation(String userName, String mutedId) {
        Response response = new Response();
        // Check if user exists
        if (userService.findUserByUsername(userName) == null) {
            response.setSuccess(false);
            response.setMessage("User does not exist");
            return response;
        }
        // Check if muted user exists
        if (userService.findUserByUsername(mutedId) == null) {
            response.setSuccess(false);
            response.setMessage("Muted user does not exist");
            return response;
        }
        // Check if user is trying to mute or unmute himself
        if (userName.equals(mutedId)) {
            response.setSuccess(false);
            response.setMessage("User cannot mute or unmute himself");
            return response;
        }
        return response;
    }

    @Override
    public Response muteUser(String userName, String mutedId) {
        Response response = validOperation(userName, mutedId);
        if (!response.isSuccess()) {
            return response;
        }
        // Check if user is already muted
        if (isMuted(userName, mutedId)) {
            response.setSuccess(false);
            response.setMessage("User is already muted");
            return response;
        }
        // Mute user
        Mutes mute = new Mutes();
        mute.setUserName(userName);
        mute.setMutedId(mutedId);
        mutesRepository.save(mute);
        response.setSuccess(true);
        response.setMessage("User muted successfully");
        response.setData(mute);
        return response;
    }

    @Override
    public Response unMuteUser(String userName, String mutedId) {

        Response response = validOperation(userName, mutedId);
        if (!response.isSuccess()) {
            return response;
        }
        // Check if user is already unmuted
        if (!isMuted(userName, mutedId)) {
            response.setSuccess(false);
            response.setMessage("User is not muted");
            return response;
        }
        // Unmute user
        mutesRepository.deleteById(new MutesId(userName, mutedId));
        response.setSuccess(true);
        response.setMessage("User unmuted successfully");
        return response;
    }

    @Override
    public List<User> getMutedUsers(String userName) {
        List<String> mutedUsers = mutesRepository.findMutedUsersByUserName(userName);
        List<User> users = new ArrayList<>();
        for (String mutedUser : mutedUsers) {
            User user = userService.findUserByUsername(mutedUser);
            users.add(user);
        }
        return users;
    }

    @Override
    public boolean isMuted(String userName, String mutedId) {
        return mutesRepository.findById(new MutesId(userName, mutedId)).isPresent();
    }

    @Override
    public int countMutedUsers(String userName) {
        return mutesRepository.findMutedUsersByUserName(userName).size();
    }

    @Override
    public int countHasMutedByUsers(String mutedId) {
        return mutesRepository.countHasMutedByUsers(mutedId);
    }

    @Override
    public List<User> getUsersWhoMutedUser(String mutedId) {
        List<String> users = mutesRepository.findUsersWhoMutedUser(mutedId);
        List<User> userList = new ArrayList<>();
        for (String user : users) {
            User u = userService.findUserByUsername(user);
            userList.add(u);
        }
        return userList;
    }

}
