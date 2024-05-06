package com.Twitter.org.services;

import java.util.List;

import com.Twitter.org.Models.Tweets.Tweets;

public interface TweetsService {


    List<Tweets> GetAllTweetsAvailable();
    List<String> GetAllTweetsForFollowingHomeFeed(String username);
    List<Tweets> GetAllTweetsForUser(String username);
    Tweets newTweet(Tweets tweet);
    void removeTweet(int tweetId);
}
