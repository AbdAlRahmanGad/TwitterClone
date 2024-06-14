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
public class TweetsSummaryDto {
    private Integer id;
    private LocalDateTime dateTweeted;
    private String authorId;
    private Integer parentId;
    private Integer originalPost;
    private String content;
    private byte[] media;
    private Boolean repost;
    private Boolean comment;
}