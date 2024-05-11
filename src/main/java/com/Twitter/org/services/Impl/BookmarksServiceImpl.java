package com.Twitter.org.services.Impl;

import com.Twitter.org.Models.Tweets.Bookmarks.Bookmarks;
import com.Twitter.org.Models.Tweets.Bookmarks.BookmarksId;
import com.Twitter.org.Models.Response;
import com.Twitter.org.Models.Tweets.Tweets;
import com.Twitter.org.Repository.BookmarksRepository;
import com.Twitter.org.Repository.TweetsRepository;
import com.Twitter.org.services.BookmarksService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookmarksServiceImpl implements BookmarksService {
    private final TweetsRepository tweetsRepository;
    private final BookmarksRepository bookmarksRepository;

    public BookmarksServiceImpl(TweetsRepository tweetsRepository, BookmarksRepository bookmarksRepository) {
        this.tweetsRepository = tweetsRepository;
        this.bookmarksRepository = bookmarksRepository;
    }

    @Override
    public Response addBookmark(String username, int tweetId) {
        Response response = new Response();
        Optional<Tweets> tweet = tweetsRepository.findById(tweetId);
        if (tweet.isPresent()) {
            // check if the tweet is already bookmarked
            if (bookmarksRepository.isBookmarked(username, tweetId) > 0) {
                response.setSuccess(false);
                response.setMessage("Tweet already bookmarked");
                return response;
            }

            Bookmarks bookmark = new Bookmarks();
            bookmark.setUsername(username);
            bookmark.setTweetId(tweetId);
            bookmark.setTweet(tweet.get());

            // increment the bookmarks counter for the tweet
            tweet.get().setBookmarksNumber(tweet.get().getBookmarksNumber() + 1);
            tweetsRepository.save(tweet.get());

            bookmarksRepository.save(bookmark);

            response.setMessage("Tweet bookmarked successfully");
            response.setData(tweet.get());
        } else {
            response.setSuccess(false);
            response.setMessage("Tweet not found");
        }
        return response;
    }

    @Override
    public Response removeBookmark(String username, int tweetId) {
        Response response = new Response();

        BookmarksId bookmarksId = new BookmarksId();
        bookmarksId.setUsername(username);
        bookmarksId.setTweetId(tweetId);

        Optional<Bookmarks> bookmark = bookmarksRepository.findById(bookmarksId);

        if (bookmark.isPresent()) {
            response.setData(bookmark.get().getTweet());

            // decrement the bookmarks counter for the tweet
            bookmark.get().getTweet().setBookmarksNumber(bookmark.get().getTweet().getBookmarksNumber() - 1);
            tweetsRepository.save(bookmark.get().getTweet());

            bookmarksRepository.delete(bookmark.get());
            response.setMessage("Bookmark removed successfully");
        } else {
            response.setSuccess(false);
            response.setMessage("Bookmark not found");
        }
        return response;
    }

    @Override
    public List<Tweets> getBookmarks(String username) {
        List<Bookmarks> bookmarks = bookmarksRepository.findByUsername(username);
        List<Tweets> tweets = new ArrayList<>();
        for (Bookmarks bookmark : bookmarks) {
            tweets.add(bookmark.getTweet());
        }
        return tweets;
    }

    @Override
    public int countBookmarks(int tweetId) {
        return bookmarksRepository.countByTweetId(tweetId);
    }

    @Override
    public boolean isBookmarked(String username, int tweetId) {
        return bookmarksRepository.isBookmarked(username, tweetId) > 0;
    }


    // This method updates the bookmarks counter for all tweets on startup
    // If you inserted some bookmarks manually in the database, you can call this method to update the counter
    @Transactional
    public void updateBookmarksCountersOnStartup() {
        // Get all tweets
        List<Tweets> tweets = (List<Tweets>) tweetsRepository.findAll();
        for (Tweets tweet : tweets) {
            int count = bookmarksRepository.countByTweetId(tweet.getId());
            tweet.setBookmarksNumber(count);
            tweetsRepository.save(tweet);
        }
    }

}
