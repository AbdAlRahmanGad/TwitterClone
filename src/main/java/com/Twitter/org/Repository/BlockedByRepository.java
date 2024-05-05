//package com.Twitter.org.Repository;
//
//import java.util.Set;
//
//import com.Twitter.org.Models.Users.Blocked;
//import com.Twitter.org.Models.Users.BlockedBy;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface BlockedByRepository extends CrudRepository<BlockedBy, String>{
//    @Query(value = "SELECT who_blocked_me FROM blockedBy WHERE blockedBy.user_name = ?1", nativeQuery = true)
//    Set<Blocked> getAllUserWhoBlockedMe(String userName);
//
//    // TODO(query): Add a query to check if the I am blocked by the user
//
//}