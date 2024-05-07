package com.Twitter.org.Repository;

import com.Twitter.org.Models.Likes;
import com.Twitter.org.Models.Tweets.Tweets;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikesRepository extends CrudRepository<Likes, Integer> {
    @Query("SELECT l FROM Likes l WHERE l.username = :username AND l.tweetId = :tweetId")
    Likes findByUsernameAndTweetId(@Param("username") String username, @Param("tweetId") int tweetId);

    @Query("SELECT COUNT(l) FROM Likes l WHERE l.tweetId = :tweetId")
    int countLikes(@Param("tweetId") int tweetId);

    @Query("SELECT t FROM Tweets t WHERE t.id IN (SELECT l.tweetId FROM Likes l WHERE l.username = :username)")
    List<Tweets> findLikedTweets(String username);
}