package com.Twitter.org.controllers;

import com.Twitter.org.Models.Response;
import com.Twitter.org.Models.Tweets.Tweets;
import com.Twitter.org.Models.dto.TweetsDto.TweetsCreateDto;
import com.Twitter.org.services.Authentication.AuthenticationService;
import com.Twitter.org.services.Replies.RepliesService;
import com.Twitter.org.services.Tweets.TweetsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Replies", description = "Replies operations")
@RestController
@RequestMapping("/comment")
public class RepliesController {

    private final RepliesService repliesService;
    private final TweetsService tweetsService;
    private final AuthenticationService authenticationService;

    @Autowired
    public RepliesController(RepliesService repliesService, TweetsService tweetsService, AuthenticationService authenticationService) {
        this.repliesService = repliesService;
        this.tweetsService = tweetsService;
        this.authenticationService = authenticationService;
    }

    @Contract(pure = true)
    private static String getTweetAuthorUsername(@NotNull TweetsCreateDto tweet) {
        return tweet.getAuthorId();
    }

    @Contract(pure = true)
    @Nullable
    private String getTweetAuthorUsername(@NotNull Integer tweetId) {
        Tweets tweet = tweetsService.getTweetById(tweetId);
        return tweet == null ? null : tweet.getAuthorId();
    }

    // User Replay to a tweet
    // Takes in a tweet object and the parent tweet id
    // Returns the replay tweet object and the parent tweet as ResponseEntity<Response>
    @Operation(summary = "Comment on a tweet")
    @PostMapping("/create")
    public ResponseEntity<?> comment(@RequestBody TweetsCreateDto commentTweet) {
        final ResponseEntity<?> Unauthorized_or_Unauthenticated = authenticationService.sameUserOrAdminAuthenticated(getTweetAuthorUsername(commentTweet));
        if (Unauthorized_or_Unauthenticated.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return Unauthorized_or_Unauthenticated;
        }

        Response response = repliesService.comment(commentTweet);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    // User remove a replay
    // Takes in the id of the replay to be deleted
    // Returns the parent tweet object as ResponseEntity<Response>
    @Operation(summary = "Delete a comment")
    @DeleteMapping("/{replayId}")
    public ResponseEntity<?> deleteComment(@PathVariable int replayId) {
        final ResponseEntity<?> Unauthorized_or_Unauthenticated = authenticationService.sameUserOrAdminAuthenticated(getTweetAuthorUsername(replayId));
        if (Unauthorized_or_Unauthenticated.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return Unauthorized_or_Unauthenticated;
        }

        String tweetAuthorUsername = getTweetAuthorUsername(replayId);
        if (tweetAuthorUsername == null)
            return ResponseEntity.badRequest().body("The tweet does not exist or is not a valid tweet!");

        Response response = repliesService.deleteComment(replayId);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    // Count number of replies on a tweet
    // Takes in the id of the tweet
    // Returns the number of replies as int
    @Operation(summary = "Count the number of replies on a tweet")
    @GetMapping("/count/{tweetId}")
    public ResponseEntity<?> countReplies(@PathVariable int tweetId) {
        boolean authenticated = authenticationService.getAuthenticatedUsername() != null;

        if (!authenticated) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized or Unauthenticated");
        }

        int count = repliesService.countReplies(tweetId);
        return ResponseEntity.ok(count);
    }

}
