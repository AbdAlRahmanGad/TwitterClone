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

//    @Query(
//            value = "SELECT u FROM User u WHERE u.userName IN (SELECT f.followingUser FROM Following f WHERE f.userName = ?1)",
//            nativeQuery = false)
//    List<User> GetAllFollowing(String userName);

    // TODO(query): Add a query to check if I am following the user

}