package com.Twitter.org.services.Likes;

import com.Twitter.org.Models.Tweets.Likes.Likes;
import com.Twitter.org.Models.Response;
import com.Twitter.org.Models.Tweets.Tweets;
import com.Twitter.org.Models.Users.User;
import com.Twitter.org.Repository.LikesRepository;
import com.Twitter.org.Repository.TweetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Response addLike(String username, int tweetId) {
        // find the tweet by id
        Optional<Tweets> tweet = tweetsRepository.findById(tweetId);
        Response response = new Response();
        // if tweet is present
        if (tweet.isPresent()) {
            // check if the tweet is already liked
            if (likesRepository.findByUsernameAndTweetId(username, tweetId) != null) {
                response.setSuccess(false);
                response.setMessage("Tweet already liked");
                return response;
            }


            // create a new like
            Likes like = new Likes();
            like.setUsername(username);
            like.setTweetId(tweetId);
            like.setTweet(tweet.get());

            // increment the likes counter for the tweet
            tweet.get().setLikesNumber(tweet.get().getLikesNumber() + 1);
            tweetsRepository.save(tweet.get());

            // save the like
            likesRepository.save(like);

            response.setMessage("Tweet liked successfully");
            response.setData(tweet.get());
            response.setSuccess(true);
        } else {
            response.setSuccess(false);
            response.setMessage("Tweet not found");
        }
        return response;
    }

    @Override
    public Response removeLike(String username, int tweetId) {
        // find the like by username and tweetId
        Likes like = likesRepository.findByUsernameAndTweetId(username, tweetId);
        Response response = new Response();
        // if like is present
        if (like != null) {
            // delete the like
            response.setData(like.getTweet());

            // decrement the likes counter for the tweet
            Tweets tweet = like.getTweet();
            tweet.setLikesNumber(tweet.getLikesNumber() - 1);
            if (tweet.getLikesNumber() < 0) {
                tweet.setLikesNumber(0);
            }
            tweetsRepository.save(tweet);


            likesRepository.delete(like);
            response.setMessage("Tweet unliked successfully");
            response.setSuccess(true);
        } else {
            response.setSuccess(false);
            response.setMessage("Like not found");
        }
        return response;
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

    @Override
    public List<User> getUsersWhoLikedTweet(int tweetId) {
        return likesRepository.findUsersWhoLikedTweet(tweetId);
    }

    // This method updates the likes counter for all tweets on startup
    // If you inserted some likes manually in the database, you can call this method to update the likes counter
    @Transactional
    public void updateLikesCountersOnStartup() {
        // Get all tweets
        List<Tweets> allTweets = (List<Tweets>) tweetsRepository.findAll();

        // For each tweet, count the number of likes and update the likes counter
        for (Tweets tweet : allTweets) {
            int likesCount = likesRepository.countLikes(tweet.getId());
            tweet.setLikesNumber(likesCount);
            tweetsRepository.save(tweet);
        }
    }

}
