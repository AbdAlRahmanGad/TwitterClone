package com.Twitter.org.Repository;

import java.util.Set;

import com.Twitter.org.Models.Users.Followers;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowersRepository extends CrudRepository<Followers, String>{

    @Query(
            value = "SELECT follower_id FROM Followers f WHERE f.user_name = ?1",
            nativeQuery = true)
    Set<String> GetAllFollowers(String userName);

    // TODO(query): Add a query to check if the user is following me
}