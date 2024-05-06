package com.Twitter.org.Models.Tweets;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.Type;
import org.hibernate.type.descriptor.jdbc.VarbinaryJdbcType;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Tweets")
public class Tweets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_tweeted", insertable=false)
    private LocalDateTime dateTweeted;
    // TODO do the same for every other date

    @Column(name = "author_id", nullable = false)
    private String authorId;

    @Column(name = "parent_id")
    private Integer parentId;

    @Column(name = "content")
    private String content;

//    @Type(type="org.hibernate.type.BinaryType")
    @JdbcType(VarbinaryJdbcType.class)
    @Column(name = "media")
    private byte[] media;

    // TODO do the same for every other default value in other tables
    @Column(name = "bookmarks_number", insertable=false, columnDefinition = "integer default 0")
    private Integer bookmarksNumber;

    @Column(name = "replies_number", insertable=false, columnDefinition = "integer default 0")
    private Integer repliesNumber;

    @Column(name = "likes_number", insertable=false, columnDefinition = "integer default 0")
    private Integer likesNumber;

    @Column(name = "repost_number", insertable=false, columnDefinition = "integer default 0")
    private Integer repostNumber;

    @Column(name = "is_repost")
    private Boolean repost;

    @Column(name = "original_post")
    private Integer originalPost;
}
