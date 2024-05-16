package com.Twitter.org.services;

import com.Twitter.org.Models.Tweets.Tweets;

import java.util.List;

public interface TweetsService {

    // Get Tweet by tweetId
    Tweets getTweetById(int tweetId);

    // Get all tweets available
    List<Tweets> GetAllTweetsAvailable();

    // Get all tweets available (all tweets from all users excluding muted and blocked users)
    List<Tweets> GetAllTweetsAvailableExcludingMutedAndBlocked(String username);

    // Get all tweets from people whom user follows
    List<Tweets> GetAllTweetsForFollowingHomeFeed(String username);

    // Get all tweets for a user
    List<Tweets> GetAllTweetsByUser(String username);

    // Create a new tweet
    Tweets newTweet(Tweets tweet);

    // Remove a tweet
    void removeTweet(int tweetId);
}
