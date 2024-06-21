package com.Twitter.org.services.Tweets;

import com.Twitter.org.Models.Tweets.Tweets;
import com.Twitter.org.Repository.TweetsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TweetsServiceImpl implements TweetsService {

    final TweetsRepository tweetsRepository;

    public TweetsServiceImpl(TweetsRepository tweetsRepository) {
        this.tweetsRepository = tweetsRepository;
    }


    @Override
    public Tweets getTweetById(int tweetId) {
        return tweetsRepository.findById(tweetId).orElse(null);
    }

    @Override
    public List<Tweets> GetAllTweetsAvailable() {
        return (List<Tweets>) tweetsRepository.findAll();
    }


    @Override
    public List<Tweets> GetAllTweetsForFollowingHomeFeed(String username) {
        return tweetsRepository.findAllTweetsFromFollowedUsersExcludingMuted(username);
    }

    @Override
    public List<Tweets> GetAllTweetsAvailableExcludingMutedAndBlocked(String username) {
        return tweetsRepository.GetAllTweetsAvailableExcludingMutedAndBlocked(username);
    }

    // Get all tweets created by a user
    @Override
    public List<Tweets> GetAllTweetsByUser(String username) {
        return tweetsRepository.findByAuthorId(username);

    }

    // Create a new tweet
    @Override
    public Tweets newTweet(Tweets tweet) {
        // to avoid that the id is supplied, make it null
        tweet.setId(null);
        tweet.setParentId(null);
        tweet.setOriginalPost(null);
        return tweetsRepository.save(tweet);
    }

    // Remove a tweet
    @Override
    public void removeTweet(int tweetId) {
        tweetsRepository.deleteById(tweetId);
    }
}
