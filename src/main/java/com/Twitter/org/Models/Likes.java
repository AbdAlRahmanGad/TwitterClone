package com.Twitter.org.Models;

import com.Twitter.org.Models.Tweets.Tweets;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Likes")
@IdClass(LikesId.class)
public class Likes {

    @Id
    @Column(name = "tweet_id", nullable = false)
    private Integer tweetId;

    @Id
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "date_liked")
    private LocalDateTime dateLiked;

    // ManyToOne relationship with the tweets table
    @ManyToOne
    @JoinColumn(name = "tweet_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Tweets tweet;


    // TODO() check if Date is valid and modify it for the sorting of the bookmarks

    //    TODO
    //4 check if post is liked by a certain user (for search)

}