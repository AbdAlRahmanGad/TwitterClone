package com.Twitter.org.services.Impl;

import java.util.List;

import com.Twitter.org.Models.Tweets.Tweets;
import com.Twitter.org.Repository.TweetsRepository;
import com.Twitter.org.services.TweetsService;
import org.springframework.stereotype.Service;

@Service
public class TweetsServiceImpl implements TweetsService {

    final TweetsRepository tweetsRepository;

    public TweetsServiceImpl(TweetsRepository tweetsRepository) {
        this.tweetsRepository = tweetsRepository;
    }
    @Override
    public List<Tweets> GetAllTweetsAvailable() {
        return (List<Tweets>) tweetsRepository.findAll();
    }

    @Override
    public List<String> GetAllTweetsForFollowingHomeFeed(String username) {
        return tweetsRepository.findAllTweetsFromUserFollowing(username);
    }

    @Override
    public List<Tweets> GetAllTweetsForUser(String username) {
        return tweetsRepository.findByAuthorId(username);

    }

    @Override
    public Tweets newTweet(Tweets tweet) {
        return tweetsRepository.save(tweet);
    }

    @Override
    public void removeTweet(int tweetId) {
        tweetsRepository.deleteById(tweetId);
    }
}
