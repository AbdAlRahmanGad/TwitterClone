package com.Twitter.org.controllers;

import com.Twitter.org.Models.Response;
import com.Twitter.org.Models.Tweets.Tweets;
import com.Twitter.org.Models.dto.TweetsDto;
import com.Twitter.org.mappers.Impl.TweetsMapper;
import com.Twitter.org.services.Bookmarks.BookmarksService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookmarksController {
    private final BookmarksService bookmarksService;
    private final TweetsMapper tweetsMapper;

    public BookmarksController(BookmarksService bookmarksService, TweetsMapper tweetsMapper) {
        this.bookmarksService = bookmarksService;
        this.tweetsMapper = tweetsMapper;
    }

    // User bookmarks a tweet
    @PostMapping("/{username}/bookmarks/{tweetId}")
    public ResponseEntity<Response> bookmarkTweet(@PathVariable String username, @PathVariable int tweetId) {
        Response response = bookmarksService.addBookmark(username, tweetId);
        if (response.isSuccess()) {
            TweetsDto tweetDto = tweetsMapper.mapTo((Tweets) response.getData());
            response.setData(tweetDto);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    // User un-bookmarks a tweet
    @DeleteMapping("/{username}/bookmarks/{tweetId}")
    public ResponseEntity<Response> unbookmarkTweet(@PathVariable String username, @PathVariable int tweetId) {
        Response response = bookmarksService.removeBookmark(username, tweetId);
        if (response.isSuccess()) {
            TweetsDto tweetDto = tweetsMapper.mapTo((Tweets) response.getData());
            response.setData(tweetDto);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    // Check if a user has bookmarked a tweet
    @GetMapping("/{username}/bookmarks/{tweetId}")
    public boolean isBookmarked(@PathVariable String username, @PathVariable int tweetId) {
        return bookmarksService.isBookmarked(username, tweetId);
    }

    // Count the number of bookmarks for a tweet
    @GetMapping("/tweets/{tweetId}/bookmarks/count")
    public int countBookmarks(@PathVariable int tweetId) {
        return bookmarksService.countBookmarks(tweetId);
    }

    // Get all tweets bookmarked by a user
    @GetMapping("/{username}/bookmarks/tweets")
    public List<TweetsDto> getBookmarkedTweets(@PathVariable String username) {
        List<Tweets> bookmarkedTweets = bookmarksService.getBookmarks(username);
        List<TweetsDto> bookmarkedTweetsDtos;
        bookmarkedTweetsDtos = bookmarkedTweets.stream()
                .map(tweetsMapper::mapTo)
                .toList();
        return bookmarkedTweetsDtos;
    }

}
