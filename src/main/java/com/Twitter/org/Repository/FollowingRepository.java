package com.Twitter.org.Repository;


import java.util.List;
import java.util.Set;

import com.Twitter.org.Models.Users.Following;
import com.Twitter.org.Models.Users.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowingRepository extends CrudRepository<Following, String>{

//    @Query(
//            value = "SELECT following_id FROM Following f WHERE f.user_name = ?1",
//            nativeQuery = true)
//    Set<String> GetAllFollowing(String userName);
    @Query(
            value = "SELECT * FROM twitter_user WHERE user_name IN (SELECT following_id FROM Following WHERE user_name = ?1)",
            nativeQuery = true)
    List<String> GetAllFollowing(String userName);

    // query to remove a follower
    @Query(
            value = "DELETE FROM Following WHERE user_name = ?1 AND following_id = ?2",
            nativeQuery = true)
    void removeFollower(String userName, String userToFollow);

    // query to add a follower
    @Query(
            value = "INSERT INTO Following (user_name, following_id) VALUES (?1, ?2)",
            nativeQuery = true)
    void addFollower(String userName, String userToFollow);

//    @Query(
//            value = "SELECT u FROM User u WHERE u.userName IN (SELECT f.followingUser FROM Following f WHERE f.userName = ?1)",
//            nativeQuery = false)
//    List<User> GetAllFollowing(String userName);

    // TODO(query): Add a query to check if I am following the user

}