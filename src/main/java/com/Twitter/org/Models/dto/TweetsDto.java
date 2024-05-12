package com.Twitter.org.Models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TweetsDto {

    private Integer id;

    private LocalDateTime dateTweeted;

    private String authorId;

    private Integer parentId;

    private String content;

    private byte[] media;

    @Builder.Default
    private Integer bookmarksNumber = 0;

    @Builder.Default
    private Integer repliesNumber = 0;

    @Builder.Default
    private Integer likesNumber = 0;

    @Builder.Default
    private Integer repostNumber = 0;

    private Boolean repost;

    private Boolean comment;

    private Integer originalPost;
}
