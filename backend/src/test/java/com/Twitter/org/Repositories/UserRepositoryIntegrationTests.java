package com.Twitter.org.Repositories;

import com.Twitter.org.Models.Users.User;
import com.Twitter.org.Repository.UserRepository;
import com.Twitter.org.TestUtility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserRepositoryIntegrationTests {

    private UserRepository underTest;

    @Autowired
    public UserRepositoryIntegrationTests(UserRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatUserCanBeCreatedAndRecalled() {
        User user = TestUtility.createTestUserA();
        underTest.save(user);
        Optional<User> result = underTest.findById(user.getUserName());
        assertThat(result).isPresent();

        System.out.println(result.get());
        System.out.println(user);

        assertThat(result.get()).isEqualTo(user);
    }

    @Test
    public void testThatMultipleUsersCanBeCreatedAndRecalled() {
        User user = TestUtility.createTestUserA();
        underTest.save(user);
        User userB = TestUtility.createTestUserB();
        underTest.save(userB);
        User userC = TestUtility.createTestUserC();
        underTest.save(userC);

        Iterable<User> result = underTest.findAll();
        assertThat(result)
                .hasSize(3).
                contains(user, userB, userC);
        System.out.println(result);
    }
//
    @Test
    public void retrieveFollowingUsers() {
//        User user = TestUtility.createTestUserA();
//        underTest.save(user);
//        User userB = TestUtility.createTestUserB();
//        underTest.save(userB);
//        User userC = TestUtility.createTestUserC();
//        underTest.save(userC);
//
//        Iterable<User> result = underTest.
//        assertThat(result)
//                .hasSize(0);
//        System.out.println(result);
    }






//    public void testThatAuthorCanBeUpdated() {
//        User authorA = TestDataUtil.createTestAuthorA();
//        underTest.save(authorA);
//        authorA.setName("UPDATED");
//        underTest.save(authorA);
//        Optional<Author> result = underTest.findById(authorA.getId());
//        assertThat(result).isPresent();
//        assertThat(result.get()).isEqualTo(authorA);
//    }
//
//    @Test
//    public void testThatAuthorCanBeDeleted() {
//        User authorA = TestDataUtil.createTestAuthorA();
//        underTest.save(authorA);
//        underTest.deleteById(authorA.getId());
//        Optional<User> result = underTest.findById(authorA.getId());
//        assertThat(result).isEmpty();
//    }
//
//    @Test
//    public void testThatGetAuthorsWithAgeLessThan() {
//        User testAuthorA = TestDataUtil.createTestAuthorA();
//        underTest.save(testAuthorA);
//        User testAuthorB = TestDataUtil.createTestAuthorB();
//        underTest.save(testAuthorB);
//        User testAuthorC = TestDataUtil.createTestAuthorC();
//        underTest.save(testAuthorC);
//
//        Iterable<User> result = underTest.ageLessThan(50);
//        assertThat(result).containsExactly(testAuthorB, testAuthorC);
//    }
}