package com.Twitter.org.Models.Tweets;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Tweets")
public class Tweets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "date_tweeted")
//    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTweeted;

    @Column(name = "author_id", nullable = false)
    private String authorId;

    @Column(name = "parent_id")
    private Integer parentId;

    @Column(name = "content")
    private String content;

    @Lob
    @Column(name = "media")
    private byte[] media;

    @Column(name = "bookmarks_number")
    private Integer bookmarksNumber;

    @Column(name = "replies_number")
    private Integer repliesNumber;

    @Column(name = "likes_number")
    private Integer likesNumber;

    @Column(name = "repost_number")
    private Integer repostNumber;

    @Column(name = "is_repost")
    private Boolean isRepost;

    @Column(name = "original_post")
    private Integer originalPost;
}
