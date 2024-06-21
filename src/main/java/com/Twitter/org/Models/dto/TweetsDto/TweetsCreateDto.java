package com.Twitter.org.Models.dto.TweetsDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TweetsCreateDto {
    private String authorId;
    private Integer parentId;
    private String content;
    private byte[] media;
}