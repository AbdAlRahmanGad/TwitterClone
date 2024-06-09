package com.Twitter.org.controllers;

import java.util.List;

import com.Twitter.org.Models.Response;
import com.Twitter.org.Models.Tweets.Quote;
import com.Twitter.org.Models.Tweets.Tweets;
import com.Twitter.org.Models.dto.TweetsDto;
import com.Twitter.org.mappers.Impl.TweetsMapper;
import com.Twitter.org.services.Reposts.RepostsServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RepostsController {

    private final TweetsMapper tweetsMapper;
    private final RepostsServiceImpl repostsService;

    public RepostsController(TweetsMapper tweetsMapper, RepostsServiceImpl repostsService) {
        this.tweetsMapper = tweetsMapper;
        this.repostsService = repostsService;
    }

    // Endpoint to getNumberOfReposts
    @GetMapping(path = "/{tweetId}/numberOfReposts")
    public int hasUserRepostedTweet(@PathVariable("tweetId") int tweetId) {
        return repostsService.getNumberOfReposts(tweetId);
    }


    // Endpoint to list all reposts of a tweet
    @GetMapping(path = "/{tweetId}/reposts")
    public List<TweetsDto> listReposts(@PathVariable("tweetId") int tweetId) {
        List<Tweets> reposts = repostsService.getAllRepostsOfTweet(tweetId);
        return reposts.stream()
                .map(tweetsMapper::mapTo)
                .toList();
    }

    // Endpoint to delete a repost
    @DeleteMapping(path = "/{username}/reposts/{tweetId}")
    public void deleteRepost(@PathVariable("username") String username, @PathVariable("tweetId") int tweetId) {
        repostsService.deleteRepost(username, tweetId);
    }

    // Endpoint to check if a user has reposted a tweet
    @GetMapping(path = "/{username}/reposts/{tweetId}")
    public boolean hasUserRepostedTweet(@PathVariable("username") String username, @PathVariable("tweetId") int tweetId) {
        return repostsService.hasUserRepostedTweet(username, tweetId);
    }

    // Endpoint to repost a tweet
    @PostMapping(path = "/{username}/reposts/{tweetId}")
    public ResponseEntity<Response> repostTweet(@PathVariable("username") String username, @PathVariable("tweetId") int tweetId) {
        // if user has already reposted the tweet, return the tweet
        Response response = repostsService.repostTweet(username, tweetId);
        if (response.isSuccess()) {
            // Map the Tweets object to a TweetsDto object
            TweetsDto tweetDto = tweetsMapper.mapTo((Tweets) response.getData());
            // Set the TweetsDto object to the response data
            response.setData(tweetDto);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    // Endpoint to quote a tweet
    @PostMapping(path = "/{username}/quote/{tweetId}")
    public ResponseEntity<Response> quoteTweet(@PathVariable("username") String username, @PathVariable("tweetId") int tweetId, @RequestBody Quote comment) {
        // if user has already Quoted the tweet, return the tweet
        Response response = repostsService.quoteTweet(username, tweetId, comment.getContent());
        if (response.isSuccess()) {
            // Map the Tweets object to a TweetsDto object
            TweetsDto tweetDto = tweetsMapper.mapTo((Tweets) response.getData());
            // Set the TweetsDto object to the response data
            response.setData(tweetDto);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }


}
