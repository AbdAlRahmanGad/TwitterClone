package com.Twitter.org.controllers;

import com.Twitter.org.Models.Response;
import com.Twitter.org.Models.Tweets.Tweets;
import com.Twitter.org.Models.dto.TweetsDto.TweetsDetailsDto;
import com.Twitter.org.mappers.Mapper;
import com.Twitter.org.services.Bookmarks.BookmarksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Bookmarks", description = "Bookmarks operations")
@RestController
public class BookmarksController {
    private final BookmarksService bookmarksService;
    private final Mapper<Tweets, TweetsDetailsDto> tweetsDetailsDtoMapper;

    public BookmarksController(BookmarksService bookmarksService, Mapper<Tweets, TweetsDetailsDto> tweetsDetailsDtoMapper) {
        this.bookmarksService = bookmarksService;
        this.tweetsDetailsDtoMapper = tweetsDetailsDtoMapper;
    }

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

    // User bookmarks a tweet
    @Operation(summary = "Bookmark a tweet")
    @PostMapping("/{username}/bookmarks/{tweetId}")
    public ResponseEntity<?> bookmarkTweet(@PathVariable String username, @PathVariable int tweetId) {
        final ResponseEntity<?> Unauthorized_or_Unauthenticated = GetUserDetailsResponse(username);
        if (Unauthorized_or_Unauthenticated != null) return Unauthorized_or_Unauthenticated;

        Response response = bookmarksService.addBookmark(username, tweetId);
        if (response.isSuccess()) {
            TweetsDetailsDto tweetDto = tweetsDetailsDtoMapper.mapTo((Tweets) response.getData());
            response.setData(tweetDto);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    // User un-bookmarks a tweet
    @Operation(summary = "Delete bookmark")
    @DeleteMapping("/{username}/bookmarks/{tweetId}")
    public ResponseEntity<?> unbookmarkTweet(@PathVariable String username, @PathVariable int tweetId) {
        final ResponseEntity<?> Unauthorized_or_Unauthenticated = GetUserDetailsResponse(username);
        if (Unauthorized_or_Unauthenticated != null) return Unauthorized_or_Unauthenticated;

        Response response = bookmarksService.removeBookmark(username, tweetId);
        if (response.isSuccess()) {
            TweetsDetailsDto tweetDto = tweetsDetailsDtoMapper.mapTo((Tweets) response.getData());
            response.setData(tweetDto);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    // Check if a user has bookmarked a tweet
    @Operation(summary = "Check user bookmarked a tweet")
    @GetMapping("/{username}/bookmarks/{tweetId}")
    public ResponseEntity<?> isBookmarked(@PathVariable String username, @PathVariable int tweetId) {
        final ResponseEntity<?> Unauthorized_or_Unauthenticated = GetUserDetailsResponse(username);
        if (Unauthorized_or_Unauthenticated != null) return Unauthorized_or_Unauthenticated;
        boolean isBookmarked = bookmarksService.isBookmarked(username, tweetId);
        return ResponseEntity.ok(isBookmarked);
    }

    // Count the number of bookmarks for a tweet
    @Operation(summary = "Count tweet's bookmarks")
    @GetMapping("/tweets/{tweetId}/bookmarks/count")
    public ResponseEntity<?> countBookmarks(@PathVariable int tweetId) {
        return ResponseEntity.ok(bookmarksService.countBookmarks(tweetId));
    }

    // Get all tweets bookmarked by a user
    @Operation(summary = "Get user's bookmarks")
    @GetMapping("/{username}/bookmarks/tweets")
    public ResponseEntity<?> getBookmarkedTweets(@PathVariable String username) {
        final ResponseEntity<?> Unauthorized_or_Unauthenticated = GetUserDetailsResponse(username);
        if (Unauthorized_or_Unauthenticated != null) return Unauthorized_or_Unauthenticated;

        List<Tweets> bookmarkedTweets = bookmarksService.getBookmarks(username);
        List<TweetsDetailsDto> bookmarkedTweetsDtos;
        bookmarkedTweetsDtos = bookmarkedTweets.stream()
                .map(tweetsDetailsDtoMapper::mapTo)
                .toList();
        return ResponseEntity.ok(bookmarkedTweetsDtos);
    }

}
