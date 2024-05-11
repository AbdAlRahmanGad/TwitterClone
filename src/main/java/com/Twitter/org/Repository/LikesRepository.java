package com.Twitter.org.Repository;

import com.Twitter.org.Models.Tweets.Likes.Likes;
import com.Twitter.org.Models.Tweets.Likes.LikesId;
import com.Twitter.org.Models.Tweets.Tweets;
import com.Twitter.org.Models.Users.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikesRepository extends CrudRepository<Likes, LikesId> {

    // Check if a user has liked a tweet, Returns a Like object if the user has liked the tweet
    @Query("SELECT l FROM Likes l WHERE l.username = :username AND l.tweetId = :tweetId")
    Likes findByUsernameAndTweetId(@Param("username") String username, @Param("tweetId") int tweetId);

    // Count the number of likes for a tweet
    @Query("SELECT COUNT(l) FROM Likes l WHERE l.tweetId = :tweetId")
    int countLikes(@Param("tweetId") int tweetId);

    // Get all the tweets liked by a user
    @Query("SELECT t FROM Tweets t WHERE t.id IN (SELECT l.tweetId FROM Likes l WHERE l.username = :username)")
    List<Tweets> findLikedTweets(String username);

    // Get all users who liked a tweet
    @Query("SELECT u FROM User u WHERE u.userName IN (SELECT l.username FROM Likes l WHERE l.tweetId = :tweetId)")
    List<User> findUsersWhoLikedTweet(int tweetId);
}