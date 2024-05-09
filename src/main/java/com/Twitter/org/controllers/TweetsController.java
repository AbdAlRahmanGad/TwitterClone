package com.Twitter.org.controllers;

import java.util.List;

import com.Twitter.org.Models.Tweets.Tweets;
import com.Twitter.org.Models.dto.TweetsDto;
import com.Twitter.org.mappers.Mapper;
import com.Twitter.org.services.Impl.TweetsServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TweetsController {

    private final TweetsServiceImpl tweetsService;

    private final Mapper<Tweets, TweetsDto> tweetsMapper;

    public TweetsController(TweetsServiceImpl tweetsService, Mapper<Tweets, TweetsDto> tweetsMapper) {
        this.tweetsService = tweetsService;
        this.tweetsMapper = tweetsMapper;
    }


    @PostMapping("/status/createTweet")
    public ResponseEntity<TweetsDto> createTweet(@RequestBody TweetsDto tweetDto) {
        Tweets tweet = tweetsMapper.mapFrom(tweetDto);
        Tweets createdTweet = tweetsService.newTweet(tweet);
        return new ResponseEntity<>(tweetsMapper.mapTo(createdTweet), HttpStatus.CREATED);
    }

    @GetMapping("home/allTweets")
    public List<TweetsDto> getAllTweets() {
        List<Tweets> allTweets = tweetsService.GetAllTweetsAvailable();
        List<TweetsDto>allTweetsDtos =
                allTweets.stream()
                        .map(tweetsMapper::mapTo)
                        .toList();

        return allTweetsDtos;
    }


//    TODO
//    @GetMapping("{username}/home/followingTweets")
//    public List<TweetsDto> getTweetsForFollowingForUser(@PathVariable String username) {
//    }

//    TODO this function should be in the user controller
    @GetMapping("/{username}/tweets")
    public List<TweetsDto> getTweetsForUser(@PathVariable String username) {
        List<Tweets> userTweets = tweetsService.GetAllTweetsForUser(username);
        List<TweetsDto>userTweetsDtos =
                userTweets.stream()
                         .map(tweetsMapper::mapTo)
                         .toList();
        return userTweetsDtos;
    }

    @DeleteMapping("/status/deleteTweet/{tweetId}")
    public void deleteTweet(@PathVariable int tweetId) {
        tweetsService.removeTweet(tweetId);
    }



}
