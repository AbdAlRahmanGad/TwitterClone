package com.Twitter.org.services.Impl;

import java.util.List;

import com.Twitter.org.Models.Tweets.Tweets;
import com.Twitter.org.Repository.TweetsRepository;
import com.Twitter.org.services.RepostsService;
import org.springframework.stereotype.Service;

@Service
public class RepostsServiceImpl implements RepostsService {

    final TweetsRepository tweetsRepository;

    public RepostsServiceImpl(TweetsRepository tweetsRepository) {
        this.tweetsRepository = tweetsRepository;
    }

    @Override
    public int getNumberOfReposts(int tweetId) {
        return tweetsRepository.countReposts(tweetId);
    }

    @Override
    public List<Tweets> getAllRepostsOfTweet(int tweetId) {
        return tweetsRepository.findReposts(tweetId);
    }

    @Override
    public Tweets repostTweet(String username, int tweetId) {
      return tweetsRepository.save(new Tweets().
                builder()
                .authorId(username)
                .parentId(tweetId)
                .repost(true)
                .originalPost(tweetId)
                .build());
    }

    @Override
    public Tweets quoteTweet(String username, int tweetId, String comment) {
        Tweets originalTweet = tweetsRepository.findById(tweetId).get();
        originalTweet.setRepostNumber(originalTweet.getRepostNumber() + 1);
        tweetsRepository.save(originalTweet);
        return tweetsRepository.save(new Tweets().
                builder()
                .authorId(username)
                .content(comment)
                .parentId(tweetId)
                .repost(true)
                .originalPost(tweetId)
                .build());
    }

    @Override
    public void deleteRepost(String username, int tweetId) {
        Tweets originalTweet = tweetsRepository.findById(tweetId).get();
        originalTweet.setRepostNumber(originalTweet.getRepostNumber() + 1);
        tweetsRepository.save(originalTweet);
        tweetsRepository.deleteRepost(tweetId, username);
    }

    @Override
    public boolean hasUserRepostedTweet(String username, int tweetId) {
        return  tweetsRepository.hasUserRepostedTweet(tweetId, username) > 0;
    }
}
