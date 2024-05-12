package com.Twitter.org.Models.Tweets.Bookmarks;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BookmarksId implements Serializable {
    private Integer tweetId;
    private String username;
}
