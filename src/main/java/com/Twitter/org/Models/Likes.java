package com.Twitter.org.Models;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Likes")
public class Likes {

    @Id
    @Column(name = "tweet_id", nullable = false)
    private Integer tweetId;

    @Id
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "date_liked")
    private Date dateLiked;
    // TODO() check if Date is valid and modify it for the sorting of the bookmarks

    //    TODO
    //1- check if a user liked a post
    //2- count all liked of a post
    //3- get all posts a user liked
    //4 check if post is liked by a certain user (for search)

}