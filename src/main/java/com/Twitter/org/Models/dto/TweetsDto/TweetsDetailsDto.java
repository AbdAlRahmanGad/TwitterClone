package com.Twitter.org.Models.dto.TweetsDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TweetsDetailsDto {
    private Integer id;
    private LocalDateTime dateTweeted;
    private String authorId;
    private Integer parentId;
    private String content;
    private byte[] media;
    private Integer bookmarksNumber;
    private Integer repliesNumber;
    private Integer likesNumber;
    private Integer repostNumber;
    private Boolean repost;
    private Boolean comment;
    private Integer originalPost;
}