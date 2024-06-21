package com.Twitter.org.Models.Tweets;

import com.Twitter.org.Models.Tweets.Bookmarks.Bookmarks;
import com.Twitter.org.Models.Tweets.Likes.Likes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.VarbinaryJdbcType;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Tweets")
public class Tweets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_tweeted", insertable = false)
    private LocalDateTime dateTweeted;

    @Column(name = "author_id", nullable = false)
    private String authorId;

    @Column(name = "parent_id")
    private Integer parentId;

    @Column(name = "content")
    private String content;

    @JdbcType(VarbinaryJdbcType.class)
    @Column(name = "media")
    private byte[] media;

    @Column(name = "bookmarks_number", insertable = false, columnDefinition = "integer default 0")
    private Integer bookmarksNumber;

    @Column(name = "replies_number", insertable = false, columnDefinition = "integer default 0")
    private Integer repliesNumber;

    @Column(name = "likes_number", insertable = false, columnDefinition = "integer default 0")
    private Integer likesNumber;

    @Column(name = "repost_number", insertable = false, columnDefinition = "integer default 0")
    private Integer repostNumber;

    @Column(name = "is_repost")
    private Boolean repost;

    @Column(name = "is_comment")
    private Boolean comment;

    @Column(name = "original_post")
    private Integer originalPost;

    @OneToMany(mappedBy = "tweetId", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Likes> likes;

    @OneToMany(mappedBy = "tweetId", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Bookmarks> bookmarks;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Replies> replies;
}
