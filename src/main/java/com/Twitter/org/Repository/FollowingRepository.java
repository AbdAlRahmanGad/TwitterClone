package com.Twitter.org.Repository;


import com.Twitter.org.Models.Users.Following;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowingRepository extends CrudRepository<Following, String> {

    //    @Query(
//            value = "SELECT following_id FROM Following f WHERE f.user_name = ?1",
//            nativeQuery = true)
//    Set<String> GetAllFollowing(String userName);
    @Query(
            value = "SELECT * FROM twitter_user WHERE user_name IN (SELECT following_id FROM Following WHERE user_name = ?1)",
            nativeQuery = true)
    List<String> GetAllFollowing(String userName);

    // query to remove a follower
    @Modifying
    @Query(
            value = "DELETE FROM Following WHERE user_name = ?1 AND following_id = ?2",
            nativeQuery = true)
    void removeFollow(String userName, String userToFollow);

    // query to add a follower
    @Modifying
    @Query(
            value = "INSERT INTO Following (user_name, following_id) VALUES (?1, ?2)",
            nativeQuery = true)
    void addFollower(String userName, String userToFollow);

//    @Query(
//            value = "SELECT u FROM User u WHERE u.userName IN (SELECT f.followingUser FROM Following f WHERE f.userName = ?1)",
//            nativeQuery = false)
//    List<User> GetAllFollowing(String userName);

    // query to get all followers
    @Query(
            value = "SELECT * FROM twitter_user WHERE user_name IN (SELECT user_name FROM Following WHERE following_id = ?1)",
            nativeQuery = true)
    List<String> GetAllFollowers(String userName);

    // query to check if specific user is following another user, boolean
    // isFollowing("user1", "user2") returns true if user1 is following user2
    @Query(
            value = "SELECT EXISTS(SELECT * FROM Following WHERE user_name = ?1 AND following_id = ?2)",
            nativeQuery = true)
    boolean isFollowing(String userName, String userToFollow);


}