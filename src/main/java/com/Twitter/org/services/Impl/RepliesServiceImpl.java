package com.Twitter.org.services.Impl;

import com.Twitter.org.Models.Response;
import com.Twitter.org.Models.Tweets.Replies;
import com.Twitter.org.Models.Tweets.Tweets;
import com.Twitter.org.Repository.RepliesRepository;
import com.Twitter.org.Repository.TweetsRepository;
import com.Twitter.org.mappers.Impl.TweetsMapper;
import com.Twitter.org.services.RepliesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RepliesServiceImpl implements RepliesService {

    private final TweetsRepository tweetsRepository;
    private final TweetsMapper TweetsMapper;
    private final RepliesRepository repliesRepository;

    public RepliesServiceImpl(TweetsRepository tweetsRepository, com.Twitter.org.mappers.Impl.TweetsMapper tweetsMapper, RepliesRepository repliesRepository) {
        this.tweetsRepository = tweetsRepository;
        TweetsMapper = tweetsMapper;
        this.repliesRepository = repliesRepository;
    }

    Response validParent(int parentId) {
        Response response = new Response();
        Optional<Tweets> parentTweet = tweetsRepository.findById(parentId);
        if (parentTweet.isPresent()) {
            response.setSuccess(true);
            response.setData(parentTweet.get());
            return response;
        }
        response.setSuccess(false);
        response.setMessage("You can't comment to a non-exist tweet!");
        return response;
    }

    @Override
    public Response comment(Tweets comment) {
        // 1. Validate the request
        int parentTweetId = comment.getParentId();
        Response response = validParent(parentTweetId);
        if (!response.isSuccess()) {
            return response;
        }

        // 2. Create the entity
        Tweets parentTweet = (Tweets) response.getData();
        parentTweet.setRepliesNumber(parentTweet.getRepliesNumber() + 1);
        tweetsRepository.save(parentTweet);

        comment.setOriginalPost(parentTweetId); // Set the originalPostId of the comment
        comment.setComment(true); // Set the comment to true

        // 3. Save the entity
        comment.setId(null); // Ignore the id if it is supplied, To avoid conflicts
        tweetsRepository.save(comment);

        // 4. Create the Replies object
        Replies reply = new Replies(parentTweet, comment);
        repliesRepository.save(reply);

        // 6. Return a response
        Map<String, Object> data = new HashMap<>();

        // To fix cyclic reference, we will map the parent tweet to a DTO object
        data.put("parentTweet", TweetsMapper.mapTo(parentTweet));
        data.put("comment", TweetsMapper.mapTo(comment));
        return new Response(true, "Comment created successfully!", data);
    }

    @Override
    public Response deleteComment(int commentTweetId) {
        Response response = new Response();
        Optional<Tweets> comment = tweetsRepository.findById(commentTweetId);
        if (comment.isEmpty() || !comment.get().getComment()) {
            response.setSuccess(false);
            response.setMessage("The comment does not exist!");
            return response;
        }

        Tweets parentTweet = comment.get();
        int parentId = parentTweet.getParentId();
        Optional<Tweets> parentTweetOptional = tweetsRepository.findById(parentId);
        String message = "";
        if (parentTweetOptional.isEmpty()) {
            response.setMessage("Note, The parent tweet does not exist!\n");
        } else {
            parentTweet = parentTweetOptional.get();
            parentTweet.setRepliesNumber(parentTweet.getRepliesNumber() - 1);
            tweetsRepository.save(parentTweet);
            response.setData(TweetsMapper.mapTo(parentTweet));
        }

        tweetsRepository.deleteById(commentTweetId); // Deleting the comment Tweet will delete the reply object as well
        response.setSuccess(true);
        message += "Comment deleted successfully!";
        response.setMessage(message);
        return response;
    }

    @Override
    public int countReplies(int tweetId) {
        Optional<Tweets> tweet = tweetsRepository.findById(tweetId);
        if (tweet.isEmpty()) {
            return 0;
        }
        return tweet.get().getRepliesNumber();
    }

    // This method updates the comments counter for all tweets on startup
    // if you inserted some comments manually in the database
    @Transactional
    public void updateCommentsCountersOnStartup() {
        // Get all tweets
        List<Tweets> tweets = (List<Tweets>) tweetsRepository.findAll();
        for (Tweets tweet : tweets) {
            int comments = repliesRepository.countRepliesByTweetId(tweet.getId());
            tweet.setRepliesNumber(comments);
            tweetsRepository.save(tweet);
        }
    }
}
