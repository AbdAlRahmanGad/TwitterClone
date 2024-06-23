package com.Twitter.org.controllers;

import com.Twitter.org.Models.Tweets.Tweets;
import com.Twitter.org.Models.dto.TweetsDto.TweetsCreateDto;
import com.Twitter.org.Models.dto.TweetsDto.TweetsDetailsDto;
import com.Twitter.org.mappers.Mapper;
import com.Twitter.org.services.Authentication.AuthenticationService;
import com.Twitter.org.services.Tweets.TweetsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Tweets", description = "Tweets operations")
@RestController
public class TweetsController {

    private final TweetsServiceImpl tweetsService;
    private final Mapper<Tweets, TweetsCreateDto> TweetsCreateDtoMapper;
    private final Mapper<Tweets, TweetsDetailsDto> TweetsDetailsDtoMapper;
    private final AuthenticationService authenticationService;

    @Autowired
    public TweetsController(TweetsServiceImpl tweetsService, Mapper<Tweets, TweetsCreateDto> tweetsCreateDtoMapper, Mapper<Tweets, TweetsDetailsDto> tweetsDetailsDtoMapper, AuthenticationService authenticationService) {
        this.tweetsService = tweetsService;
        TweetsCreateDtoMapper = tweetsCreateDtoMapper;
        TweetsDetailsDtoMapper = tweetsDetailsDtoMapper;
        this.authenticationService = authenticationService;
    }

    // Create a new tweet
    @Operation(summary = "Create a new tweet")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/tweets")
    public ResponseEntity<?> createTweet(@RequestBody TweetsCreateDto tweetDto) {
        final ResponseEntity<?> Unauthorized_or_Unauthenticated = authenticationService.sameUserOrAdminAuthenticated(tweetDto.getAuthorId());
        if (Unauthorized_or_Unauthenticated.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return Unauthorized_or_Unauthenticated;
        }

        // Set the authorId of the tweet to the authenticated user's username
        tweetDto.setAuthorId(tweetDto.getAuthorId());

        Tweets tweet = TweetsCreateDtoMapper.mapFrom(tweetDto);
        Tweets createdTweet = tweetsService.newTweet(tweet);
        return new ResponseEntity<>(TweetsCreateDtoMapper.mapTo(createdTweet), HttpStatus.CREATED);
    }

    // Delete a tweet by tweetId
    @Operation(summary = "Delete a tweet")
    @DeleteMapping("/tweets/{tweetId}")
    public ResponseEntity<?> deleteTweet(@PathVariable int tweetId) {
        String username = authenticationService.getAuthenticatedUsername();
        if (username == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // Get the tweet
        Tweets tweet = tweetsService.getTweetById(tweetId);

        // Check if the authenticated user is the author of the tweet
        if (tweet != null && (username.equals(tweet.getAuthorId()) || authenticationService.AdminAuthenticated())) {
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
    @Operation(summary = "Get all tweets from all users except blocked & muted users")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/tweets")
    public ResponseEntity<?> getAllTweets() {
        String username = authenticationService.getAuthenticatedUsername();

        if (username != null) {
            List<Tweets> allTweets = tweetsService.GetAllTweetsAvailableExcludingMutedAndBlocked(username);
            return new ResponseEntity<>(allTweets.stream().map(TweetsDetailsDtoMapper::mapTo).toList(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    // Get all tweets created by a user
    @Operation(summary = "Get all tweets by a user")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users/{username}/tweets")
    public ResponseEntity<?> getTweetsByUser(@PathVariable String username) {
        String authenticatedUsername = authenticationService.getAuthenticatedUsername();
        if (authenticatedUsername == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        List<Tweets> tweets = tweetsService.GetAllTweetsByUser(username);
        return new ResponseEntity<>(tweets.stream().map(TweetsDetailsDtoMapper::mapTo).toList(), HttpStatus.OK);
    }

    // Get all tweets from people whom user follows
    @Operation(summary = "Get all tweets from people whom user follows")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users/{username}/following/tweets")
    public ResponseEntity<?> getTweetsForFollowingHomeFeed(@PathVariable String username) {
        final ResponseEntity<?> Unauthorized_or_Unauthenticated = authenticationService.sameUserOrAdminAuthenticated(username);
        if (Unauthorized_or_Unauthenticated.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return Unauthorized_or_Unauthenticated;
        }

        List<Tweets> tweets = tweetsService.GetAllTweetsForFollowingHomeFeed(username);
        return new ResponseEntity<>(tweets.stream().map(TweetsDetailsDtoMapper::mapTo).toList(), HttpStatus.OK);
    }

    // Get a tweet by tweetId
    @Operation(summary = "Get a tweet by tweetId")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/tweets/{tweetId}")
    public ResponseEntity<?> getTweetById(@PathVariable int tweetId) {
        String username = authenticationService.getAuthenticatedUsername();
        if (username == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Tweets tweet = tweetsService.getTweetById(tweetId);
        if (tweet == null) {
            return new ResponseEntity<>("Tweet does not exist or you are blocked by the author", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(TweetsDetailsDtoMapper.mapTo(tweet), HttpStatus.OK);
    }
}
