package com.Twitter.org.Models;

import com.Twitter.org.Models.Tweets.Tweets;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Bookmarks")
@IdClass(BookmarksId.class)
public class Bookmarks {

    @Id
    @Column(name = "tweet_id", nullable = false)
    private Integer tweetId;

    @Id
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "date_bookmarked")
    private LocalDateTime dateBookmarked;

    // ManyToOne relationship with the tweets table
    // Many Bookmarks can be associated with one Tweet
    @ManyToOne
    @JoinColumn(name = "tweet_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Tweets tweet;


    // automatically set the dateLiked when a bookmark is created
    @PrePersist
    public void prePersist() {
        this.dateBookmarked = LocalDateTime.now();
    }


    // TODO() check if Date is valid and modify it for the sorting of the bookmarks

}