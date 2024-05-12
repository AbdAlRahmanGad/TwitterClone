package com.Twitter.org.services.Impl;

import com.Twitter.org.Models.Tweets.Tweets;
import com.Twitter.org.Repository.TweetsRepository;
import com.Twitter.org.services.RepostsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        Tweets originalTweet = tweetsRepository.findById(tweetId).get();
        originalTweet.setRepostNumber(originalTweet.getRepostNumber() + 1);
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
        Tweets tweet = tweetsRepository.findById(tweetId).get();
        Tweets originalTweet = tweetsRepository.findById(tweet.getOriginalPost()).get();
        originalTweet.setRepostNumber(originalTweet.getRepostNumber() - 1);
        tweetsRepository.save(originalTweet);
        tweetsRepository.deleteRepost(tweet.getOriginalPost(), username);
    }

    @Override
    public boolean hasUserRepostedTweet(String username, int tweetId) {
        return tweetsRepository.hasUserRepostedTweet(tweetId, username) > 0;
    }

    // This method updates the reposts counter for all tweets on startup
    // If you inserted some reposts manually in the database
    @Transactional
    public void updateRepostsCountersOnStartup() {
        // Get all tweets
        List<Tweets> allTweets = (List<Tweets>) tweetsRepository.findAll();

        // For each tweet, count the number of reposts and update the reposts counter
        for (Tweets tweet : allTweets) {
            int repostsCount = tweetsRepository.countReposts(tweet.getId());
            tweet.setRepostNumber(repostsCount);
        }
    }
}
