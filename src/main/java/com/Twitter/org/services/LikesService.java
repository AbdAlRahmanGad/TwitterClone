package com.Twitter.org.services;

import com.Twitter.org.Models.Tweets.Tweets;

import java.util.List;

public interface LikesService {
    void addLike(String username, int tweetId);
    void removeLike(String username, int tweetId);
    boolean isLiked(String username, int tweetId);
    int countLikes(int tweetId);
    List<Tweets> getLikedTweets(String username);
}
