package com.Twitter.org.controllers;

import com.Twitter.org.Models.Tweets.Tweets;
import com.Twitter.org.Models.dto.TweetsDto;
import com.Twitter.org.mappers.Impl.TweetsMapper;
import com.Twitter.org.services.LikesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LikesController {

    private final LikesService likesService;
    private final TweetsMapper tweetsMapper;


    public LikesController(LikesService likesService, TweetsMapper tweetsMapper) {
        this.likesService = likesService;
        this.tweetsMapper = tweetsMapper;
    }


    // User likes a tweet
    @PostMapping("/{username}/tweets/{tweetId}/likes")
    public ResponseEntity<String> likeTweet(@PathVariable String username, @PathVariable int tweetId) {
        likesService.addLike(username, tweetId);
        return ResponseEntity.ok("Tweet with ID " + tweetId + " was liked by user " + username);
    }

    // User unlikes a tweet
    @DeleteMapping("/{username}/tweets/{tweetId}/likes")
    public ResponseEntity<String> unlikeTweet(@PathVariable String username, @PathVariable int tweetId) {
        likesService.removeLike(username, tweetId);
        return ResponseEntity.ok("Tweet with ID " + tweetId + " was unliked by user " + username);
    }

    // Check if a user has liked a tweet
    @GetMapping("/{username}/tweets/{tweetId}/likes")
    public boolean isLiked(@PathVariable String username, @PathVariable int tweetId) {
        return likesService.isLiked(username, tweetId);
    }

    // Count the number of likes for a tweet
    @GetMapping("/tweets/{tweetId}/likes/count")
    public int countLikes(@PathVariable int tweetId) {
        return likesService.countLikes(tweetId);
    }

    // Get all tweets liked by a user
    @GetMapping("/{username}/likes/tweets")
    public List<TweetsDto> getLikedTweets(@PathVariable String username) {
        List<Tweets> likedTweets = likesService.getLikedTweets(username);
        List<TweetsDto> likedTweetsDtos;
        likedTweetsDtos = likedTweets.stream()
                .map(tweetsMapper::mapTo)
                .toList();
        return likedTweetsDtos;
    }

}
