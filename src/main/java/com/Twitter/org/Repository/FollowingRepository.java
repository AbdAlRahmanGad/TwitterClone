package com.Twitter.org.Repository;


import com.Twitter.org.Models.Users.Following.Following;
import com.Twitter.org.Models.Users.Following.FollowingId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowingRepository extends CrudRepository<Following, FollowingId> {

}