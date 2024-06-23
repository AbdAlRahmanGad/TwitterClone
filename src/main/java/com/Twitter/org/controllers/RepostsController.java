package com.Twitter.org.controllers;

import com.Twitter.org.Models.Response;
import com.Twitter.org.Models.Tweets.Quote;
import com.Twitter.org.Models.Tweets.Tweets;
import com.Twitter.org.Models.dto.TweetsDto.TweetsDetailsDto;
import com.Twitter.org.Models.dto.TweetsDto.TweetsSummaryDto;
import com.Twitter.org.mappers.Mapper;
import com.Twitter.org.services.Authentication.AuthenticationService;
import com.Twitter.org.services.Reposts.RepostsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Reposts", description = "Reposts operations")
@RestController
public class RepostsController {

    private final Mapper<Tweets, TweetsSummaryDto> tweetsSummaryDtoMapper;
    private final Mapper<Tweets, TweetsDetailsDto> tweetsDetailsDtoMapper;
    private final RepostsServiceImpl repostsService;
    private final AuthenticationService authenticationService;

    @Autowired
    public RepostsController(Mapper<Tweets, TweetsSummaryDto> tweetsSummaryDtoMapper, Mapper<Tweets, TweetsDetailsDto> tweetsDetailsDtoMapper, RepostsServiceImpl repostsService, AuthenticationService authenticationService) {
        this.tweetsSummaryDtoMapper = tweetsSummaryDtoMapper;
        this.tweetsDetailsDtoMapper = tweetsDetailsDtoMapper;
        this.repostsService = repostsService;
        this.authenticationService = authenticationService;
    }

    // Endpoint to repost a tweet
    @Operation(summary = "Repost a tweet")
    @PostMapping(path = "/{username}/reposts/{tweetId}")
    public ResponseEntity<?> repostTweet(@PathVariable("username") String username, @PathVariable("tweetId") int tweetId) {
        final ResponseEntity<?> Unauthorized_or_Unauthenticated = authenticationService.sameUserOrAdminAuthenticated(username);
        if (Unauthorized_or_Unauthenticated.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return Unauthorized_or_Unauthenticated;
        }

        Response response = repostsService.repostTweet(username, tweetId);
        if (response.isSuccess()) {
            TweetsDetailsDto tweetDto = tweetsDetailsDtoMapper.mapTo((Tweets) response.getData());
            response.setData(tweetDto);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @Operation(summary = "Delete a repost")
    @DeleteMapping(path = "/{username}/reposts/{tweetId}")
    public ResponseEntity<?> deleteRepost(@PathVariable("username") String username, @PathVariable("tweetId") int tweetId) {
        final ResponseEntity<?> Unauthorized_or_Unauthenticated = authenticationService.sameUserOrAdminAuthenticated(username);
        if (Unauthorized_or_Unauthenticated.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return Unauthorized_or_Unauthenticated;
        }

        boolean isDeleted = repostsService.deleteRepost(username, tweetId);
        if (isDeleted) {
            return ResponseEntity.ok("Deleted repost successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Repost not found or not a valid repost");
        }
    }

    // Endpoint to quote a tweet
    @Operation(summary = "Quote a tweet")
    @PostMapping(path = "/{username}/quote/{tweetId}")
    public ResponseEntity<?> quoteTweet(@PathVariable("username") String username, @PathVariable("tweetId") int tweetId, @RequestBody Quote comment) {
        final ResponseEntity<?> Unauthorized_or_Unauthenticated = authenticationService.sameUserOrAdminAuthenticated(username);
        if (Unauthorized_or_Unauthenticated.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return Unauthorized_or_Unauthenticated;
        }

        Response response = repostsService.quoteTweet(username, tweetId, comment.getContent());
        if (response.isSuccess()) {
            TweetsDetailsDto tweetDto = tweetsDetailsDtoMapper.mapTo((Tweets) response.getData());
            response.setData(tweetDto);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    // Endpoint to check if a user has reposted a tweet
    @Operation(summary = "Check if a user has reposted a tweet")
    @GetMapping(path = "/{username}/reposts/{tweetId}")
    public ResponseEntity<?> hasUserRepostedTweet(@PathVariable("username") String username, @PathVariable("tweetId") int tweetId) {
        final ResponseEntity<?> Unauthorized_or_Unauthenticated = authenticationService.sameUserOrAdminAuthenticated(username);
        if (Unauthorized_or_Unauthenticated.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return Unauthorized_or_Unauthenticated;
        }

        boolean hasReposted = repostsService.hasUserRepostedTweet(username, tweetId);
        return ResponseEntity.ok(hasReposted);
    }


    // Endpoint to list all reposts of a tweet
    @Operation(summary = "List all reposts of a tweet")
    @GetMapping(path = "/{tweetId}/reposts")
    public ResponseEntity<?> listReposts(@PathVariable("tweetId") int tweetId) {
        List<Tweets> reposts = repostsService.getAllRepostsOfTweet(tweetId);
        return ResponseEntity.ok(reposts.stream()
                .map(tweetsSummaryDtoMapper::mapTo)
                .toList());
    }

    // Endpoint to getNumberOfReposts
    @Operation(summary = "Get number of reposts")
    @GetMapping(path = "/{tweetId}/numberOfReposts")
    public int CountReposts(@PathVariable("tweetId") int tweetId) {
        return repostsService.getNumberOfReposts(tweetId);
    }
}
