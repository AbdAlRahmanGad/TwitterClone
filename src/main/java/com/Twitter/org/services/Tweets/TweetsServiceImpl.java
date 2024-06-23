package com.Twitter.org.services.Tweets;

import com.Twitter.org.Models.Tweets.Tweets;
import com.Twitter.org.Repository.TweetsRepository;
import com.Twitter.org.services.Authentication.AuthenticationService;
import com.Twitter.org.services.Blocks.BlocksService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TweetsServiceImpl implements TweetsService {

    private final TweetsRepository tweetsRepository;
    private final BlocksService blocksService;
    private final AuthenticationService authenticationService;


    public TweetsServiceImpl(TweetsRepository tweetsRepository, BlocksService blocksService, AuthenticationService authenticationService) {
        this.tweetsRepository = tweetsRepository;
        this.blocksService = blocksService;
        this.authenticationService = authenticationService;
    }


    @Override
    public Tweets getTweetById(int tweetId) {
        String authenticatedUsername = authenticationService.getAuthenticatedUsername();
        Tweets tweet = tweetsRepository.findById(tweetId).orElse(null);
        if (tweet == null) {
            return null;
        }
        if (blocksService.isBlocked(tweet.getAuthorId(), authenticatedUsername)) {
            return null;
        }
        return tweet;
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
        // the authenticated must not be blocked by the user
        String authenticatedUsername = authenticationService.getAuthenticatedUsername();
        if (blocksService.isBlocked(username, authenticatedUsername)) {
            return null;
        }
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
