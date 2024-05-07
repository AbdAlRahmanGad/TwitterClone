package com.Twitter.org.Repository;

import com.Twitter.org.Models.Tweets.Tweets;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetsRepository extends CrudRepository<Tweets, Integer> {


    //    Tweets findByTweetId(int tweetId);
//    Tweets findByUser(User user);
    List<Tweets> findByAuthorId(String username);

    // GetAllTweetsForFollowingHomeFeed
//    implement get all tweets from poeople whome user follows
//
//    make a native full sql query
    @Query(value = "SELECT t.* FROM tweets t WHERE author_id  IN ( SELECT following.following_id from following WHERE user_name = ?1)", nativeQuery = true)
    List<String> findAllTweetsFromUserFollowing(String username);

//    Tweets findByTweet(String tweet);
//    Tweets findByTweetTime(String tweetTime);
//    Tweets findByTweetLikes(int tweetLikes);
//    Tweets findByTweetRetweets(int tweetRetweets);
//    Tweets findByTweetComments(int tweetComments);
}
