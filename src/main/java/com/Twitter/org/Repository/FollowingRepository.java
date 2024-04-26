package com.Twitter.org.Repository;


import java.util.Set;

import com.Twitter.org.Models.Users.Following;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowingRepository extends CrudRepository<Following, String>{

    @Query(
            value = "SELECT following_id FROM Following f WHERE f.user_name = ?1",
            nativeQuery = true)
    Set<String> GetAllFollowing(String userName);

    // TODO(query): Add a query to check if I am following the user

}