package com.Twitter.org.services.Following;

import com.Twitter.org.Models.Response;
import com.Twitter.org.Repository.FollowingRepository;
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
    public Response addFollow(String username, String userToFollow) {

        Response response = new Response();

            // check if the user is already following the other user
            if (username.equals(userToFollow)) {
                response.setSuccess(false);
                response.setMessage("Can't Follow youself");
                return response;
            }
            if (followingRepository.isFollowing(username, userToFollow)) {
                response.setSuccess(false);
                response.setMessage("Already following " + userToFollow);
                return response;
            }

            followingRepository.addFollower(username, userToFollow);

            response.setMessage("Started following " + userToFollow + " successfully");
            response.setSuccess(true);
        return response;
    }

    @Override
    public Response removeFollow(String username, String userToUnfollow) {

        Response response = new Response();
        if (username.equals(userToUnfollow)) {
            response.setSuccess(false);
            response.setMessage("Can't Unfollow youself");
            return response;
        }
        // check if the user is already following the other user
        if (!followingRepository.isFollowing(username, userToUnfollow)) {
            response.setSuccess(false);
            response.setMessage("Already not following " + userToUnfollow);
            return response;
        }

        followingRepository.removeFollow(username, userToUnfollow);

        response.setMessage("Unfollowed " + userToUnfollow + " successfully");
        response.setSuccess(true);
        return response;
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
