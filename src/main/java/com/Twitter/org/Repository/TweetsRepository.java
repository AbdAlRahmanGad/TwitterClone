package com.Twitter.org.Repository;

import com.Twitter.org.Models.Tweets.Tweets;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetsRepository extends CrudRepository<Tweets, Integer> {


    // Tweets findByTweetId(int tweetId);
    List<Tweets> findByAuthorId(String username);

    // GetAllTweetsForFollowingHomeFeed
    // implement get all tweets from poeople whome user follows

    @Query(value = "SELECT t.* FROM tweets t WHERE author_id  IN ( SELECT following.following_id from following WHERE user_name = ?1)", nativeQuery = true)
    List<String> findAllTweetsFromUserFollowing(String username);

    /**
     * reposts
     */
    @Query(value = "SELECT count(*) FROM tweets WHERE parent_id = ?1", nativeQuery = true)
    int countReposts(int tweetId);

    @Query(value = "SELECT * FROM tweets WHERE parent_id = ?1", nativeQuery = true)
    List<Tweets> findReposts(int tweetId);

    // check if a user has reposted a tweet
    @Query(value = "SELECT count(*) FROM tweets WHERE parent_id = ?1 AND author_id = ?2", nativeQuery = true)
    int hasUserRepostedTweet(int tweetId, String username);

    // check if a user has quoted a tweet
    @Query(value = "SELECT count(*) FROM tweets WHERE parent_id = ?1 AND author_id = ?2 AND content IS NOT NULL", nativeQuery = true)
    int hasUserQuotedTweet(int tweetId, String username);


    // delete a repost
    @Query(value = "DELETE FROM tweets WHERE parent_id = ?1 AND author_id = ?2", nativeQuery = true)
    void deleteRepost(int tweetId, String username);


//    Tweets findByTweet(String tweet);
//    Tweets findByTweetTime(String tweetTime);
//    Tweets findByTweetLikes(int tweetLikes);
//    Tweets findByTweetRetweets(int tweetRetweets);
//    Tweets findByTweetComments(int tweetComments);
}
