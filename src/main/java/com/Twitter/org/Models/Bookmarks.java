package com.Twitter.org.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Bookmarks")
public class Bookmarks {

    @Id
    @Column(name = "tweet_id", nullable = false)
    private Integer tweetId;

    @Id
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "date_bookmarked")
    private LocalDateTime dateBookmarked;
    // TODO() check if Date is valid and modify it for the sorting of the bookmarks



}