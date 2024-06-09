package com.Twitter.org.controllers;

import com.Twitter.org.Models.Response;
import com.Twitter.org.Models.Tweets.Tweets;
import com.Twitter.org.Models.dto.TweetsDto;
import com.Twitter.org.mappers.Mapper;
import com.Twitter.org.services.Replies.RepliesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class RepliesController {

    private final RepliesService repliesService;
    private final Mapper<Tweets, TweetsDto> tweetsMapper;

    public RepliesController(RepliesService repliesService, Mapper<Tweets, TweetsDto> tweetsMapper) {
        this.repliesService = repliesService;
        this.tweetsMapper = tweetsMapper;
    }

    // User Replay to a tweet
    // Takes in a tweet object and the parent tweet id
    // Returns the replay tweet object and the parent tweet as ResponseEntity<Response>
    @PostMapping("/create")
    public ResponseEntity<Response> comment(@RequestBody TweetsDto commentTweet) {
        Tweets comment = tweetsMapper.mapFrom(commentTweet);
        Response response = repliesService.comment(comment);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    // User remove a replay
    // Takes in the id of the replay to be deleted
    // Returns the parent tweet object as ResponseEntity<Response>
    @DeleteMapping("/{replayId}")
    public ResponseEntity<Response> deleteComment(@PathVariable int replayId) {
        Response response = repliesService.deleteComment(replayId);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    // Count number of replies on a tweet
    // Takes in the id of the tweet
    // Returns the number of replies as int
    @GetMapping("/count/{tweetId}")
    public int countReplies(@PathVariable int tweetId) {
        return repliesService.countReplies(tweetId);
    }

}
