package com.Twitter.org.Repository;

import java.util.Set;

import com.Twitter.org.Models.Users.Blocked;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockedRepository extends CrudRepository<Blocked, String>{


    @Query(value = "SELECT whom_i_blocked FROM blocked WHERE blocked.user_name = ?1", nativeQuery = true)
    Set<Blocked> getAllUserWhomIBlocked(String userName);

    // TODO(query): Add a query to check if the I blocked the user


}