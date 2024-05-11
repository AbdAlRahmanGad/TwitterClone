package com.Twitter.org.Repository;

import com.Twitter.org.Models.Users.Mutes.Mutes;
import com.Twitter.org.Models.Users.Mutes.MutesId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MutesRepository extends CrudRepository<Mutes, MutesId> {

    // Get all mutes of a user, Returns a list of muted users
    @Query("SELECT m.mutedId FROM Mutes m WHERE m.userName = ?1")
    List<String> findMutedUsersByUserName(String userName);

    // Count number of users who muted user, (For developer use only)
    @Query("SELECT COUNT(m.userName) FROM Mutes m WHERE m.mutedId = ?1")
    int countHasMutedByUsers(String mutedId);

    // Get list of users who muted user, (For developer use only)
    @Query("SELECT m.userName FROM Mutes m WHERE m.mutedId = ?1")
    List<String> findUsersWhoMutedUser(String mutedId);
}