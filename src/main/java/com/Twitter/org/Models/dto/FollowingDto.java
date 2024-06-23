package com.Twitter.org.Models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowingDto {
    private String follower;
    private String followed;
    private LocalDateTime followingStartDate;
}