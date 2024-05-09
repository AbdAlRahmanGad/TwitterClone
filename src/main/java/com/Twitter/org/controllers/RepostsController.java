package com.Twitter.org.controllers;

import java.util.List;

import com.Twitter.org.Models.Quote;
import com.Twitter.org.Models.Tweets.Tweets;
import com.Twitter.org.Models.dto.TweetsDto;
import com.Twitter.org.mappers.Impl.TweetsMapper;
import com.Twitter.org.services.Impl.RepostsServiceImpl;
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
        List<TweetsDto> repostsDtos = reposts.stream()
                .map(tweetsMapper::mapTo)
                .toList();
        return repostsDtos;
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
    public TweetsDto repostTweet(@PathVariable("username") String username, @PathVariable("tweetId") int tweetId) {
        Tweets repostedTweet = repostsService.repostTweet(username, tweetId);
        return tweetsMapper.mapTo(repostedTweet);
    }

    // Endpoint to quote a tweet
    @PostMapping(path = "/{username}/quote/{tweetId}")
    public TweetsDto quoteTweet(@PathVariable("username") String username, @PathVariable("tweetId") int tweetId, @RequestBody Quote comment) {
        Tweets quotedTweet = repostsService.quoteTweet(username, tweetId, comment.getContent());
        return tweetsMapper.mapTo(quotedTweet);
    }


}
