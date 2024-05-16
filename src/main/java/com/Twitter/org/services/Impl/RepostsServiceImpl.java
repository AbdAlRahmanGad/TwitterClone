package com.Twitter.org.services.Impl;

import java.util.List;

import com.Twitter.org.Models.Response;
import com.Twitter.org.Models.Tweets.Tweets;
import com.Twitter.org.Repository.TweetsRepository;
import com.Twitter.org.services.RepostsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
    public Response repostTweet(String username, int tweetId) {
        // find the tweet by id
        Optional<Tweets> tweet = tweetsRepository.findById(tweetId);
        Response response = new Response();
        // if tweet is present
        if (tweet.isPresent()) {
            // check if the tweet is already reposted
            if (tweetsRepository.hasUserRepostedTweet(tweetId, username) > 0) {
                response.setSuccess(false);
                response.setMessage("Tweet already reposted");
                return response;
            }

            // create a new repost
            Tweets repost = new Tweets().
                    builder()
                    .authorId(username)
                    .parentId(tweetId) // TODO: parentId should be null
                    .repost(true)
                    .originalPost(tweetId)
                    .build();

            // increment the reposts counter for the tweet
            tweet.get().setRepostNumber(tweet.get().getRepostNumber() + 1);
            tweetsRepository.save(tweet.get());

            // save the repost
            tweetsRepository.save(repost);

            response.setMessage("Tweet reposted successfully");
            response.setData(repost);
            response.setSuccess(true);
        } else {
            response.setSuccess(false);
            response.setMessage("Tweet not found");
        }
        return response;
    }

    @Override
    public Response quoteTweet(String username, int tweetId, String comment) {
        // find the tweet by id
        Optional<Tweets> tweet = tweetsRepository.findById(tweetId);
        Response response = new Response();
        // if tweet is present
        if (tweet.isPresent()) {
            // check if the tweet is already quoted
            if (tweetsRepository.hasUserQuotedTweet(tweetId, username) > 0) {
                response.setSuccess(false);
                response.setMessage("Tweet already quoted");
                return response;
            }

            // create a new quote
            Tweets quote = new Tweets().
                    builder()
                    .authorId(username)
                    .parentId(tweetId) // TODO: parentId should be null
                    .repost(true)
                    .content(comment)
                    .originalPost(tweetId)
                    .build();

            // increment the quotes counter for the tweet
            tweet.get().setRepostNumber(tweet.get().getRepostNumber() + 1);
            tweetsRepository.save(tweet.get());

            // save the quote
            tweetsRepository.save(quote);

            response.setMessage("Tweet quoted successfully");
            response.setData(quote);
            response.setSuccess(true);
        } else {
            response.setSuccess(false);
            response.setMessage("Tweet not found");
        }
        return response;
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
        return  tweetsRepository.hasUserRepostedTweet(tweetId, username) > 0;
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
