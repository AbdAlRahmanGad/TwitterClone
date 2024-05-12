package com.Twitter.org.Repository;

import com.Twitter.org.Models.Tweets.Bookmarks.Bookmarks;
import com.Twitter.org.Models.Tweets.Bookmarks.BookmarksId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarksRepository extends CrudRepository<Bookmarks, BookmarksId> {
    List<Bookmarks> findByUsername(String username);

    // count the number of bookmarks for a tweet
    @Query("SELECT COUNT(b) FROM Bookmarks b WHERE b.tweetId = :tweetId")
    int countByTweetId(int tweetId);

    // check if a user has bookmarked a tweet
    @Query("SELECT COUNT(b) FROM Bookmarks b WHERE b.username = :username AND b.tweetId = :tweetId")
    int isBookmarked(String username, int tweetId);
}