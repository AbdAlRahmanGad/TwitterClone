package com.Twitter.org.Repository;

import java.util.Set;

import com.Twitter.org.Models.Likes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepository extends CrudRepository<Likes, Integer>{

    // Check if a user liked a post
    // Check if post is liked by a certain user (for search)
    @Query(value = "SELECT COUNT(*) > 0 FROM Likes l WHERE l.username = ?1 AND l.tweet_id = ?2", nativeQuery = true)
    boolean hasUserLikedPost(String username, Integer tweetId);

    // Count all likes of a post
    @Query(value = "SELECT COUNT(*) FROM Likes l WHERE l.tweet_id = ?1", nativeQuery = true)
    int countLikesOfPost(Integer tweetId);

    // Get all posts a user liked
    @Query(value = "SELECT l.tweet_id FROM Likes l WHERE l.username = ?1", nativeQuery = true)
    Set<Integer> getAllPostsUserLiked(String username);

    // Get all users who liked a post
    @Query(value = "SELECT l.username FROM Likes l WHERE l.tweet_id = ?1", nativeQuery = true)
    Set<String> getAllUsersWhoLikedPost(Integer tweetId);
}