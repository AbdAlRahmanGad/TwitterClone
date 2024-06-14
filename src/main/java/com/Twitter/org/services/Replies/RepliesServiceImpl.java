package com.Twitter.org.services.Replies;

import com.Twitter.org.Models.Response;
import com.Twitter.org.Models.Tweets.Replies;
import com.Twitter.org.Models.Tweets.Tweets;
import com.Twitter.org.Models.dto.TweetsDto.TweetsCreateDto;
import com.Twitter.org.Repository.RepliesRepository;
import com.Twitter.org.Repository.TweetsRepository;
import com.Twitter.org.mappers.Impl.TweetsMapper.TweetsDetailsDtoMapper;
import com.Twitter.org.mappers.Mapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RepliesServiceImpl implements RepliesService {

    private final TweetsRepository tweetsRepository;
    private final RepliesRepository repliesRepository;
    private final Mapper<Tweets, TweetsCreateDto> tweetsCreateDtoMapper;
    private final TweetsDetailsDtoMapper tweetsDetailsDtoMapper;

    public RepliesServiceImpl(TweetsRepository tweetsRepository, RepliesRepository repliesRepository, Mapper<Tweets, TweetsCreateDto> tweetsCreateDtoMapper, TweetsDetailsDtoMapper tweetsDetailsDtoMapper) {
        this.tweetsRepository = tweetsRepository;
        this.repliesRepository = repliesRepository;
        this.tweetsCreateDtoMapper = tweetsCreateDtoMapper;
        this.tweetsDetailsDtoMapper = tweetsDetailsDtoMapper;
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

    @Transactional
    @Override
    public Response comment(@NotNull TweetsCreateDto comment) {
        Response response = validParent(comment.getParentId());
        if (!response.isSuccess()) {
            return response;
        }

        Tweets parentTweet = (Tweets) response.getData();
        parentTweet.setRepliesNumber(parentTweet.getRepliesNumber() + 1);
        tweetsRepository.save(parentTweet);

        Tweets newComment = tweetsCreateDtoMapper.mapFrom(comment);
        newComment.setOriginalPost(comment.getParentId());
        newComment.setComment(true);
        newComment.setId(null);
        tweetsRepository.save(newComment);

        Replies reply = new Replies(parentTweet, newComment);
        repliesRepository.save(reply);

        Map<String, Object> data = new HashMap<>();
        data.put("parentTweet", tweetsDetailsDtoMapper.mapTo(parentTweet));
        data.put("comment", tweetsDetailsDtoMapper.mapTo(newComment));

        return new Response(true, "Comment created successfully!", data);
    }

    @Transactional
    @Override
    public Response deleteComment(int commentTweetId) {
        Response response = new Response();

        // Find the comment tweet
        Optional<Tweets> commentOptional = tweetsRepository.findById(commentTweetId);

        // If the comment does not exist
        if (commentOptional.isEmpty()) {
            response.setSuccess(false);
            response.setMessage("The comment does not exist or is not a valid comment!");
            return response;
        }

        Tweets commentTweet = commentOptional.get();
        Integer parentId = commentTweet.getParentId();

        // If isComment is false, state that the tweet is not a comment
        if (!commentTweet.getComment()) {
            response.setSuccess(false);
            response.setMessage("The tweet is not a comment!, Use different endpoint to delete the tweet");
            return response;
        }

        // If parentId is null, set a message and delete the comment tweet
        if (parentId == null) {
            response.setMessage("Note, The parent tweet id is null!\n");
            tweetsRepository.deleteById(commentTweetId);
            response.setSuccess(true);
            response.setMessage(response.getMessage() + "Comment deleted successfully!");
            return response;
        }

        // Find the parent tweet
        Optional<Tweets> parentTweetOptional = tweetsRepository.findById(parentId);
        if (parentTweetOptional.isPresent()) {
            Tweets parentTweet = parentTweetOptional.get();
            parentTweet.setRepliesNumber(parentTweet.getRepliesNumber() - 1);
            tweetsRepository.save(parentTweet);
            response.setData(tweetsDetailsDtoMapper.mapTo(parentTweet));
        } else {
            response.setMessage("Note, The parent tweet does not exist!\n");
        }

        // Delete the comment tweet
        tweetsRepository.deleteById(commentTweetId);

        response.setSuccess(true);
        response.setMessage(response.getMessage() + "Comment deleted successfully!");
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
