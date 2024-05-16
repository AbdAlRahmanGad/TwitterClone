package com.Twitter.org.Repository;

import com.Twitter.org.Models.Tweets.Replies.Replies;
import com.Twitter.org.Models.Tweets.Replies.RepliesId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepliesRepository extends CrudRepository<Replies, RepliesId> {

    @Query("SELECT COUNT(r) FROM Replies r WHERE r.post.id = :tweetId")
    int countRepliesByTweetId(int tweetId);
}
