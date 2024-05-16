package com.Twitter.org;

import java.time.LocalDate;

import com.Twitter.org.Models.Users.Following.Following;
import com.Twitter.org.Models.Users.User;

public class TestUtility {

    public static User createTestUserA() {
        return User.builder()
                .userName("JohnDoe")
                .firstName("John")
                .lastName("Doe")
               .dateJoined(LocalDate.now())
               .build();
    }

    public static User createTestUserB() {
        return User.builder()
                   .userName("Ahmed404")
                   .firstName("man")
                   .lastName("done")
                   .bio("not an error")
                   .dateJoined(LocalDate.now())
                                      .build();
    }
    public static User createTestUserC() {
        return User.builder()
                   .userName("AbdoMan")
                   .firstName("mohamed")
                   .lastName("kamel")
                   .bio("A BIO")
                   .dateJoined(LocalDate.now())
                   .build();
    }

    public static Following createFollowingForUserAAndB() {
//        return Following.builder()
//                   .userName(createTestUserA().getUserName())
//                   .followingUser(createTestUserB())
//                   .build();
        return new Following(createTestUserA(), createTestUserB());

    }
    public static Following createFollowingForUserAAndC() {
//        return Following.builder()
//                        .userName(createTestUserA().getUserName())
//                        .followingUser(createTestUserC())
//                        .build();
        return new Following(createTestUserA(), createTestUserC());

    }

//    public static Followers createFollowerForUserAAndB() {
//        return Followers.builder()
//                        .userName(createTestUserB().getUserName())
//                        .follower(createTestUserA())
//                        .build();
//    }
}