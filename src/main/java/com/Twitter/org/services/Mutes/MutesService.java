package com.Twitter.org.services.Mutes;

import com.Twitter.org.Models.Response;
import com.Twitter.org.Models.Users.User;

import java.util.List;

public interface MutesService {
    Response muteUser(String userName, String mutedId);

    Response unMuteUser(String userName, String mutedId);

    List<User> getMutedUsers(String userName);

    boolean isMuted(String userName, String mutedId);

    int countMutedUsers(String userName);

    int countHasMutedByUsers(String mutedId);

    List<User> getUsersWhoMutedUser(String mutedId);
}
