//package com.Twitter.org.Repositories;
//
//import com.Twitter.org.Models.Users.Following.Following;
//import com.Twitter.org.Models.Users.User;
//import com.Twitter.org.Repository.FollowingRepository;
//import com.Twitter.org.TestUtility;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.List;
//import java.util.Set;
//
//
//@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//public class FollowingRepositoryTests {
//
//    private FollowingRepository underTest;
//
//    @Autowired
//    public FollowingRepositoryTests(FollowingRepository underTest) {
//        this.underTest = underTest;
//    }
//
//    @Test
//    public void testFollowingForUserA() {
//
//        Following followingForUserA = TestUtility.createFollowingForUserAAndB();
//        underTest.save(followingForUserA);
//        List<User> result = underTest.GetAllFollowing(followingForUserA.getUserName());
//
////        assertThat(result).isPresent();
//         result.forEach(following -> {
//             System.out.println(following);
//         });
////        System.out.println(TestUtility.createTestUserB());
////        System.out.println(result.get().getFollowingUser());
////        System.out.println(result.get());
//
////        assertThat(result.get().getFollowingUser()).isEqualTo(TestUtility.createTestUserB());
//    }
//
////    @Test
////    public void testMultibuleFollowing() {
////
////        Following firstoFllowingForUserA = TestUtility.createFollowingForUserAAndB();
////        Following secondFollowingForUserA = TestUtility.createFollowingForUserAAndC();
////        underTest.save(firstoFllowingForUserA);
////        Optional<Following> result = underTest.findById(firstoFllowingForUserA.getUserName());
////
////        System.out.println(result.get().getFollowingUser());
////        underTest.save(secondFollowingForUserA);
////        System.out.println(result.get().getFollowingUser());
////
////        List<Following> result2 = underTest.findByAllFollowingUsers(secondFollowingForUserA.getUserName());
////
////        assertThat(result).isPresent();
////        assertThat(result2.isEmpty()).isFalse();
////
////        System.out.println(TestUtility.createTestUserB());
////        System.out.println(result.get().getFollowingUser());
////        System.out.println(result.get());
////
////        System.out.println(TestUtility.createTestUserC());
//////        System.out.println(result2..getFollowingUser());
//////        System.out.println(result2.get());
////        for (Following following : result2) {
////            System.out.println(following.getFollowingUser());
////        }
//////        assertThat(result.get().getFollowingUser()).isEqualTo(TestUtility.createTestUserB());
//////        assertThat(result2.get().getFollowingUser()).isEqualTo(TestUtility.createTestUserC());
////    }
//}
