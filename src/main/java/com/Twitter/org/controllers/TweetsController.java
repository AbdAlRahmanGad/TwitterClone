package com.Twitter.org.controllers;

import com.Twitter.org.Models.Tweets.Tweets;
import com.Twitter.org.Models.dto.TweetsDto;
import com.Twitter.org.mappers.Mapper;
import com.Twitter.org.services.Tweets.TweetsServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TweetsController {

    private final TweetsServiceImpl tweetsService;

    private final Mapper<Tweets, TweetsDto> tweetsMapper;

    public TweetsController(TweetsServiceImpl tweetsService, Mapper<Tweets, TweetsDto> tweetsMapper) {
        this.tweetsService = tweetsService;
        this.tweetsMapper = tweetsMapper;
    }

    // Create a new tweet
    @PostMapping("/tweets")
    public ResponseEntity<TweetsDto> createTweet(@RequestBody TweetsDto tweetDto) {
        // Get the authenticated user's details
        org.springframework.security.core.userdetails.UserDetails userDetails = (org.springframework.security.core.userdetails.UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        // Set the authorId of the tweet to the authenticated user's username
        tweetDto.setAuthorId(username);

        Tweets tweet = tweetsMapper.mapFrom(tweetDto);
        Tweets createdTweet = tweetsService.newTweet(tweet);
        return new ResponseEntity<>(tweetsMapper.mapTo(createdTweet), HttpStatus.CREATED);
    }

    // Delete a tweet by tweetId
    @DeleteMapping("/tweets/{tweetId}")
    public void deleteTweet(@PathVariable int tweetId) {
        tweetsService.removeTweet(tweetId);
    }

    // Get all tweets available (all tweets from all users)
    @GetMapping("/tweets")
    public List<TweetsDto> getAllTweets() {
        List<Tweets> allTweets = tweetsService.GetAllTweetsAvailableExcludingMutedAndBlocked("username");
        return allTweets.stream()
                .map(tweetsMapper::mapTo)
                .toList();
    }


    // Get all tweets created by a user
    @GetMapping("/users/{username}/tweets")
    public List<TweetsDto> getTweetsByUser(@PathVariable String username) {
        List<Tweets> userTweets = tweetsService.GetAllTweetsByUser(username);
        return userTweets.stream()
                .map(tweetsMapper::mapTo)
                .toList();
    }


    // Get all tweets from people whom user follows
    @GetMapping("/users/{username}/following/tweets")
    public List<TweetsDto> getTweetsForFollowingHomeFeed(@PathVariable String username) {
        List<Tweets> homeFeedTweets = tweetsService.GetAllTweetsForFollowingHomeFeed(username);
        return homeFeedTweets.stream()
                .map(tweetsMapper::mapTo)
                .toList();
    }


    // Get a tweet by tweetId
    @GetMapping("/tweets/{tweetId}")
    public TweetsDto getTweetById(@PathVariable int tweetId) {
        Tweets tweet = tweetsService.getTweetById(tweetId);
        return tweetsMapper.mapTo(tweet);
    }
}
