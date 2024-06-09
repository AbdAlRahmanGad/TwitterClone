package com.Twitter.org.services.Likes;

import com.Twitter.org.Models.Response;
import com.Twitter.org.Models.Tweets.Tweets;
import com.Twitter.org.Models.Users.User;

import java.util.List;

public interface LikesService {
    // Add like to a tweet
    Response addLike(String username, int tweetId);

    // Remove like from a tweet
    Response removeLike(String username, int tweetId);

    // Check if a user has liked a tweet
    boolean isLiked(String username, int tweetId);

    // Count the number of likes for a tweet
    int countLikes(int tweetId);

    // Get all the tweets liked by a user
    List<Tweets> getLikedTweets(String username);

    // Get all users who liked a tweet
    List<User> getUsersWhoLikedTweet(int tweetId);
}
