package com.Twitter.org.controllers;

import com.Twitter.org.Models.Tweets.Tweets;
import com.Twitter.org.Models.dto.TweetsDto.TweetsCreateDto;
import com.Twitter.org.Models.dto.TweetsDto.TweetsDetailsDto;
import com.Twitter.org.Models.dto.TweetsDto.TweetsSummaryDto;
import com.Twitter.org.mappers.Mapper;
import com.Twitter.org.services.Blocks.BlocksService;
import com.Twitter.org.services.Tweets.TweetsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Tweets", description = "Tweets operations")
@RestController
public class TweetsController {

    private final TweetsServiceImpl tweetsService;
    private final BlocksService blocksService;
    private final Mapper<Tweets, TweetsCreateDto> TweetsCreateDtoMapper;
    private final Mapper<Tweets, TweetsDetailsDto> TweetsDetailsDtoMapper;
    private final Mapper<Tweets, TweetsSummaryDto> TweetsSummaryDtoMapper;

    @Autowired
    public TweetsController(TweetsServiceImpl tweetsService, BlocksService blocksService, Mapper<Tweets, TweetsCreateDto> tweetsCreateDtoMapper, Mapper<Tweets, TweetsDetailsDto> tweetsDetailsDtoMapper, Mapper<Tweets, TweetsSummaryDto> tweetsSummaryDtoMapper) {
        this.tweetsService = tweetsService;
        this.blocksService = blocksService;
        TweetsCreateDtoMapper = tweetsCreateDtoMapper;
        TweetsDetailsDtoMapper = tweetsDetailsDtoMapper;
        TweetsSummaryDtoMapper = tweetsSummaryDtoMapper;
    }

    private static ResponseEntity<?> GetUserDetailsResponse(String userName) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String authUserName = userDetails.getUsername();
        boolean isAdmin = userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        // If the user is not authenticated or not an admin
        if (!authUserName.equals(userName) && !isAdmin) {
            return ResponseEntity.status(401).body("Unauthorized or Unauthenticated");
        }
        return null;
    }

    // Create a new tweet
    @Operation(summary = "Create a new tweet")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/tweets")
    public ResponseEntity<TweetsCreateDto> createTweet(@RequestBody TweetsCreateDto tweetDto) {
        // Get the authenticated user's details
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        // Set the authorId of the tweet to the authenticated user's username
        tweetDto.setAuthorId(username);

        Tweets tweet = TweetsCreateDtoMapper.mapFrom(tweetDto);
        Tweets createdTweet = tweetsService.newTweet(tweet);
        return new ResponseEntity<>(TweetsCreateDtoMapper.mapTo(createdTweet), HttpStatus.CREATED);
    }

    // Delete a tweet by tweetId
    @Operation(summary = "Delete a tweet")
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
    @Operation(summary = "Get all tweets from all users except blocked & muted users")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/tweets")
    public ResponseEntity<?> getAllTweets() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String authenticatedUsername = userDetails.getUsername();
            // Check if the has blocked the authenticated user
            boolean isBlocked = blocksService.isBlocked(username, authenticatedUsername);
            if (!isBlocked) {
                List<Tweets> tweets = tweetsService.GetAllTweetsByUser(username);
                return new ResponseEntity<>(tweets.stream().map(TweetsDetailsDtoMapper::mapTo).toList(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("You are blocked by this user", HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }


    // Get all tweets from people whom user follows
    @Operation(summary = "Get all tweets from people whom user follows")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users/{username}/following/tweets")
    public ResponseEntity<?> getTweetsForFollowingHomeFeed(@PathVariable String username) {
        final ResponseEntity<?> Unauthorized_or_Unauthenticated = GetUserDetailsResponse(username);
        if (Unauthorized_or_Unauthenticated != null) return Unauthorized_or_Unauthenticated;

        List<Tweets> tweets = tweetsService.GetAllTweetsForFollowingHomeFeed(username);
        return new ResponseEntity<>(tweets.stream().map(TweetsDetailsDtoMapper::mapTo).toList(), HttpStatus.OK);
    }


    // Get a tweet by tweetId
    @Operation(summary = "Get a tweet by tweetId")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/tweets/{tweetId}")
    public ResponseEntity<?> getTweetById(@PathVariable int tweetId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Check if the user is authenticated
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String authenticatedUsername = userDetails.getUsername();

            Tweets tweet = tweetsService.getTweetById(tweetId);
            if (tweet != null) {
                if (!blocksService.isBlocked(tweet.getAuthorId(), authenticatedUsername)) {
                    return new ResponseEntity<>(TweetsDetailsDtoMapper.mapTo(tweet), HttpStatus.OK);
                } else {
                    // If the user is blocked by the author of the tweet, return a 403 Forbidden status code
                    return new ResponseEntity<>("You are blocked by the author of this tweet.", HttpStatus.FORBIDDEN);
                }
            } else {
                return new ResponseEntity<>("Tweet not found.", HttpStatus.NOT_FOUND);
            }
        } else {
            // If the user is not authenticated, return a 401 Unauthorized status code
            return new ResponseEntity<>("You are not authenticated.", HttpStatus.UNAUTHORIZED);
        }
    }
}
