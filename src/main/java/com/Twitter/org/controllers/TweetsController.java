package com.Twitter.org.controllers;

import com.Twitter.org.Models.Tweets.Tweets;
import com.Twitter.org.Models.dto.TweetsDto;
import com.Twitter.org.mappers.Mapper;
import com.Twitter.org.services.Blocks.BlocksService;
import com.Twitter.org.services.Tweets.TweetsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TweetsController {

    private final TweetsServiceImpl tweetsService;
    private final BlocksService blocksService;
    private final Mapper<Tweets, TweetsDto> tweetsMapper;

    @Autowired
    public TweetsController(TweetsServiceImpl tweetsService, BlocksService blocksService, Mapper<Tweets, TweetsDto> tweetsMapper) {
        this.tweetsService = tweetsService;
        this.blocksService = blocksService;
        this.tweetsMapper = tweetsMapper;
    }

    // Create a new tweet
    @PostMapping("/tweets")
    public ResponseEntity<TweetsDto> createTweet(@RequestBody TweetsDto tweetDto) {
        // Get the authenticated user's details
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        // Set the authorId of the tweet to the authenticated user's username
        tweetDto.setAuthorId(username);

        Tweets tweet = tweetsMapper.mapFrom(tweetDto);
        Tweets createdTweet = tweetsService.newTweet(tweet);
        return new ResponseEntity<>(tweetsMapper.mapTo(createdTweet), HttpStatus.CREATED);
    }

    // Delete a tweet by tweetId
    @DeleteMapping("/tweets/{tweetId}")
    public ResponseEntity<?> deleteTweet(@PathVariable int tweetId) {
        // Get the authenticated user's details
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        // Get the tweet
        Tweets tweet = tweetsService.getTweetById(tweetId);

        // Check if the authenticated user is the author of the tweet
        if (tweet != null && username.equals(tweet.getAuthorId())) {
            tweetsService.removeTweet(tweetId);
            // return ok status code, with text says that the tweet has been deleted
            return new ResponseEntity<>("Tweet has been deleted", HttpStatus.OK);
        } else {
            // If tweet does not exist
            if (tweet == null) {
                return new ResponseEntity<>("Tweet does not exist", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>("You are not Authorized to delete this tweet", HttpStatus.FORBIDDEN);
            }
        }
    }

    // Get all tweets available (all tweets from all users)
    @GetMapping("/tweets")
    public ResponseEntity<?> getAllTweets() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        if (username != null) {
            List<Tweets> allTweets = tweetsService.GetAllTweetsAvailableExcludingMutedAndBlocked(username);
            return new ResponseEntity<>(allTweets.stream().map(tweetsMapper::mapTo).toList(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }


    // Get all tweets created by a user
    @GetMapping("/users/{username}/tweets")
    public ResponseEntity<?> getTweetsByUser(@PathVariable String username) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String authenticatedUsername = userDetails.getUsername();

        if (authenticatedUsername != null && authenticatedUsername.equals(username)) {
            // Check if the has blocked the authenticated user
           boolean isBlocked = blocksService.isBlocked(username, authenticatedUsername);
            if (!isBlocked) {
                List<Tweets> tweets = tweetsService.GetAllTweetsByUser(username);
                return new ResponseEntity<>(tweets.stream().map(tweetsMapper::mapTo).toList(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("You are blocked by this user", HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }


    // Get all tweets from people whom user follows
    @GetMapping("/users/{username}/following/tweets")
    public ResponseEntity<?> getTweetsForFollowingHomeFeed(@PathVariable String username) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String authenticatedUsername = userDetails.getUsername();

        if (authenticatedUsername != null && authenticatedUsername.equals(username)) {
            List<Tweets> homeFeedTweets = tweetsService.GetAllTweetsForFollowingHomeFeed(username);
            return new ResponseEntity<>(homeFeedTweets.stream().map(tweetsMapper::mapTo).toList(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }


    // Get a tweet by tweetId
    @GetMapping("/tweets/{tweetId}")
    public ResponseEntity<TweetsDto> getTweetById(@PathVariable int tweetId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Check if the user is authenticated
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String authenticatedUsername = userDetails.getUsername();

            Tweets tweet = tweetsService.getTweetById(tweetId);
            if (tweet != null && !blocksService.isBlocked(tweet.getAuthorId(), authenticatedUsername)) {
                return new ResponseEntity<>(tweetsMapper.mapTo(tweet), HttpStatus.OK);
            } else {
                // If the user is not authenticated or blocked by the author of the tweet, return a 403 Forbidden status code
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } else {
            // If the user is not authenticated, return a 401 Unauthorized status code
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
