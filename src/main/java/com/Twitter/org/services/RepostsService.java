package com.Twitter.org.services;

import java.util.List;

import com.Twitter.org.Models.Response;
import com.Twitter.org.Models.Tweets.Tweets;

public interface RepostsService {

    // get number of reposts of a tweet
    int getNumberOfReposts(int tweetId);
    // get all reposts of a tweet
    List<Tweets> getAllRepostsOfTweet(int tweetId);
    // repost a tweet
    Response repostTweet(String username, int tweetId);
    // quote a tweet
    Response quoteTweet(String username, int tweetId, String comment);
    // delete a repost
    void deleteRepost(String username, int tweetId);
    // check if a user has reposted a tweet
    boolean hasUserRepostedTweet(String username, int tweetId);
}
