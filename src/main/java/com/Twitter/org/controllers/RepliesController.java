package com.Twitter.org.controllers;

import com.Twitter.org.Models.Response;
import com.Twitter.org.Models.Tweets.Tweets;
import com.Twitter.org.Models.dto.TweetsDto.TweetsCreateDto;
import com.Twitter.org.mappers.Mapper;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Replies", description = "Replies operations")
@RestController
@RequestMapping("/comment")
public class RepliesController {

    private final RepliesService repliesService;
    private final Mapper<Tweets, TweetsCreateDto> tweetsMapper;
    private final TweetsService tweetsService;

    @Autowired
    public RepliesController(RepliesService repliesService, Mapper<Tweets, TweetsCreateDto> tweetsMapper, TweetsService tweetsService) {
        this.repliesService = repliesService;
        this.tweetsMapper = tweetsMapper;
        this.tweetsService = tweetsService;
    }

    @Nullable
    private static ResponseEntity<?> GetUserDetailsResponse(String userName) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String authUserName = userDetails.getUsername();
        boolean isAdmin = userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        // If the user is not authenticated or not an admin
        if (!authUserName.equals(userName) && !isAdmin) {
            return new ResponseEntity<>("Unauthorized or Unauthenticated", HttpStatus.UNAUTHORIZED);
        }
        return null;
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
        final ResponseEntity<?> Unauthorized_or_Unauthenticated = GetUserDetailsResponse(getTweetAuthorUsername(commentTweet));
        if (Unauthorized_or_Unauthenticated != null) return Unauthorized_or_Unauthenticated;

        System.out.println("Comment Author = " + getTweetAuthorUsername(commentTweet));
        System.out.println("Authenticated User = " + ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());

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
        String tweetAuthorUsername = getTweetAuthorUsername(replayId);
        if (tweetAuthorUsername == null)
            return ResponseEntity.badRequest().body("The tweet does not exist or is not a valid tweet!");
        final ResponseEntity<?> Unauthorized_or_Unauthenticated = GetUserDetailsResponse(getTweetAuthorUsername(replayId));
        if (Unauthorized_or_Unauthenticated != null) return Unauthorized_or_Unauthenticated;

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
        // Any Authenticated user can view the number of replies
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            return new ResponseEntity<>("Unauthorized or Unauthenticated", HttpStatus.UNAUTHORIZED);
        }
        int count = repliesService.countReplies(tweetId);
        return ResponseEntity.ok(count);
    }

}
