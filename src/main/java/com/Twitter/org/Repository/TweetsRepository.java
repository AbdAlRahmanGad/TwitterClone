package com.Twitter.org.Repository;

import com.Twitter.org.Models.Tweets.Tweets;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TweetsRepository extends CrudRepository<Tweets, Integer> {


    // Get all tweets created by a user
    List<Tweets> findByAuthorId(String username);

    // Get all tweets from people whom user follows, excluding muted users
    @Query(value = "SELECT t.* FROM tweets t " +
                   "JOIN following f ON t.author_id = f.followed " +
                   "LEFT JOIN mutes m ON t.author_id = m.muted_id " +
                   "WHERE f.follower = ?1 AND (m.user_name IS NULL OR m.user_name != ?1)", nativeQuery = true)
    List<Tweets> findAllTweetsFromFollowedUsersExcludingMuted(String username);


    // Get all tweets available (all tweets from all users excluding muted and blocked users)
    @Query(value = "SELECT t.* FROM tweets t " +
                   "LEFT JOIN mutes m ON t.author_id = m.muted_id " +
                   "LEFT JOIN blocked b ON t.author_id = b.whom_i_blocked " +
                   "WHERE (m.user_name IS NULL OR m.user_name != ?1) AND (b.user_name IS NULL OR b.user_name != ?1)", nativeQuery = true)
    List<Tweets> GetAllTweetsAvailableExcludingMutedAndBlocked(String username);


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
    @Modifying
    @Query(value = "DELETE FROM tweets WHERE parent_id = ?1 AND author_id = ?2", nativeQuery = true)
    void deleteRepost(int tweetId, String username);

    @Modifying
    @Transactional
    @Query("UPDATE Tweets t SET t.repostNumber = t.repostNumber - 1 WHERE t.id = :id")
    void decrementRepostCount(@Param("id") Integer id);


//    Tweets findByTweet(String tweet);
//    Tweets findByTweetTime(String tweetTime);
//    Tweets findByTweetLikes(int tweetLikes);
//    Tweets findByTweetRetweets(int tweetRetweets);
//    Tweets findByTweetComments(int tweetComments);
}
