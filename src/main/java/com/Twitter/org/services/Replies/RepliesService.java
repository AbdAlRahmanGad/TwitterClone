package com.Twitter.org.services.Replies;

import com.Twitter.org.Models.Response;
import com.Twitter.org.Models.Tweets.Tweets;
import com.Twitter.org.Models.dto.TweetsDto.TweetsCreateDto;

public interface RepliesService {
    /*
    User Replay to a tweet
    returns the replay tweet object and the parent tweet
     */
    Response comment(TweetsCreateDto comment);


    /*
    User remove a replay
    returns the parent tweet object
     */
    Response deleteComment(int replayId);

    // Count number of replies on a tweet
    int countReplies(int tweetId);
}
