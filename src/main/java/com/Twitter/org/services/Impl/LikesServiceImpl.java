package com.Twitter.org.services.Impl;

import com.Twitter.org.Models.Likes;
import com.Twitter.org.Models.Tweets.Tweets;
import com.Twitter.org.Repository.LikesRepository;
import com.Twitter.org.Repository.TweetsRepository;
import com.Twitter.org.services.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikesServiceImpl implements LikesService {
    private final LikesRepository likesRepository;
    private final TweetsRepository tweetsRepository;

    @Autowired
    public LikesServiceImpl(LikesRepository likesRepository, TweetsRepository tweetsRepository) {
        this.likesRepository = likesRepository;
        this.tweetsRepository = tweetsRepository;
    }

    // Add like to a tweet
    // username: username of the user who liked the tweet
    // tweetId: id of the tweet
    @Override
    public void addLike(String username, int tweetId) {
        // find the tweet by id
        Optional<Tweets> tweet = tweetsRepository.findById(tweetId);
        // if tweet is present
        if (tweet.isPresent()) {
            // create a new like
            Likes like = new Likes();
            like.setUsername(username);
            like.setTweetId(tweetId);
            // save the like
            likesRepository.save(like);
        }
    }

    @Override
    public void removeLike(String username, int tweetId) {
        // find the like by username and tweetId
        Likes like = likesRepository.findByUsernameAndTweetId(username, tweetId);
        // if like is present
        if (like != null) {
            // delete the like
            likesRepository.delete(like);
        }
    }

    @Override
    public boolean isLiked(String username, int tweetId) {
        // find the like by username and tweetId
        Likes like = likesRepository.findByUsernameAndTweetId(username, tweetId);
        // if like is present
        return like != null;
    }

    @Override
    public int countLikes(int tweetId) {
        // find all likes for a tweet
        return likesRepository.countLikes(tweetId);
    }

    // get liked tweets by a user
    @Override
    public List<Tweets> getLikedTweets(String username) {
        // find all liked tweets by a user
        return likesRepository.findLikedTweets(username);
    }
}
