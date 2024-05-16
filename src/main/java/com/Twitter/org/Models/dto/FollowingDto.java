package com.Twitter.org.Models.dto;

import com.Twitter.org.Models.Users.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowingDto {

    private String userName;

    private User followingUser;
}