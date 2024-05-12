package com.Twitter.org.services;

import com.Twitter.org.Models.Response;
import com.Twitter.org.Models.Tweets.Tweets;

import java.util.List;

public interface BookmarksService {
    // User can bookmark a tweet
    Response addBookmark(String username, int tweetId);
    // User can un-bookmark a tweet
    Response removeBookmark(String username, int tweetId);
    // User can view all their bookmarks
    List<Tweets> getBookmarks(String username);
    // Count the number of bookmarks for a tweet
    int countBookmarks(int tweetId);
    // Check if a user has bookmarked a tweet
    boolean isBookmarked(String username, int tweetId);
}
