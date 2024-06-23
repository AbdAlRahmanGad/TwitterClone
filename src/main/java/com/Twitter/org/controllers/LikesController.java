package com.Twitter.org.controllers;

import com.Twitter.org.Models.Response;
import com.Twitter.org.Models.Tweets.Tweets;
import com.Twitter.org.Models.dto.TweetsDto.TweetsDetailsDto;
import com.Twitter.org.Models.dto.UserDto.UserResponseDto;
import com.Twitter.org.mappers.Impl.UserMapper.UserResponseMapper;
import com.Twitter.org.mappers.Mapper;
import com.Twitter.org.services.Authentication.AuthenticationService;
import com.Twitter.org.services.Likes.LikesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Likes", description = "Likes operations")
@RestController
public class LikesController {

    private final LikesService likesService;
    private final UserResponseMapper userResponseMapper;
    private final AuthenticationService authenticationService;
    private final Mapper<Tweets, TweetsDetailsDto> TweetsDetailsDtoMapper;


    public LikesController(LikesService likesService, UserResponseMapper userResponseMapper, AuthenticationService authenticationService, Mapper<Tweets, TweetsDetailsDto> tweetsDetailsDtoMapper) {
        this.likesService = likesService;
        this.userResponseMapper = userResponseMapper;
        this.authenticationService = authenticationService;
        TweetsDetailsDtoMapper = tweetsDetailsDtoMapper;
    }


    // User likes a tweet
    @Operation(summary = "Like a tweet")
    @PostMapping("/{username}/tweets/{tweetId}/likes")
    public ResponseEntity<?> likeTweet(@PathVariable String username, @PathVariable int tweetId) {
        final ResponseEntity<?> Unauthorized_or_Unauthenticated = authenticationService.sameUserOrAdminAuthenticated(username);
        if (Unauthorized_or_Unauthenticated.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return Unauthorized_or_Unauthenticated;
        }

        Response response = likesService.addLike(username, tweetId);
        if (response.isSuccess()) {
            // Map the Tweets object to a TweetsDto object
            TweetsDetailsDto tweetDto = TweetsDetailsDtoMapper.mapTo((Tweets) response.getData());
            // Set the TweetsDto object to the response data
            response.setData(tweetDto);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    // User unlikes a tweet
    @Operation(summary = "Delete a like on a tweet")
    @DeleteMapping("/{username}/tweets/{tweetId}/likes")
    public ResponseEntity<?> unlikeTweet(@PathVariable String username, @PathVariable int tweetId) {
        final ResponseEntity<?> Unauthorized_or_Unauthenticated = authenticationService.sameUserOrAdminAuthenticated(username);
        if (Unauthorized_or_Unauthenticated.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            return Unauthorized_or_Unauthenticated;
        }


        Response response = likesService.removeLike(username, tweetId);
        if (response.isSuccess()) {
            TweetsDetailsDto tweetDto = TweetsDetailsDtoMapper.mapTo((Tweets) response.getData());
            response.setData(tweetDto);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    // Check if a user has liked a tweet
    @Operation(summary = "Check user liked a tweet")
    @GetMapping("/{username}/tweets/{tweetId}/likes")
    public ResponseEntity<?> isLiked(@PathVariable String username, @PathVariable int tweetId) {
        return ResponseEntity.ok(likesService.isLiked(username, tweetId));
    }

    // Count the number of likes for a tweet
    @Operation(summary = "Count likes of a tweet")
    @GetMapping("/tweets/{tweetId}/likes/count")
    public ResponseEntity<?> countLikes(@PathVariable int tweetId) {
        return ResponseEntity.ok(likesService.countLikes(tweetId));
    }

    // Get all tweets liked by a user
    @Operation(summary = "Get all liked tweet by a user")
    @GetMapping("/{username}/likes/tweets")
    public ResponseEntity<?> getLikedTweets(@PathVariable String username) {
        List<Tweets> likedTweets = likesService.getLikedTweets(username);
        List<TweetsDetailsDto> likedTweetsDtos;
        likedTweetsDtos = likedTweets.stream()
                .map(TweetsDetailsDtoMapper::mapTo)
                .toList();
        return ResponseEntity.ok(likedTweetsDtos);
    }

    // Get all users who liked a tweet
    @Operation(summary = "Get users who liked a tweet")
    @GetMapping("/tweets/{tweetId}/likes/users")
    public ResponseEntity<?> getUsersWhoLikedTweet(@PathVariable int tweetId) {
        List<UserResponseDto> users;
        users = likesService.getUsersWhoLikedTweet(tweetId).stream()
                .map(userResponseMapper::mapTo)
                .toList();

        return ResponseEntity.ok(users);
    }
}
