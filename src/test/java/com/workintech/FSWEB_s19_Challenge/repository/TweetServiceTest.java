package com.workintech.FSWEB_s19_Challenge.repository;

import com.workintech.FSWEB_s19_Challenge.entity.Gender;
import com.workintech.FSWEB_s19_Challenge.entity.Tweet;
import com.workintech.FSWEB_s19_Challenge.entity.User;
import com.workintech.FSWEB_s19_Challenge.service.TweetServiceImpl;
import com.workintech.FSWEB_s19_Challenge.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DataJpaTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class TweetServiceTest {

    @Mock
    private TweetRepository tweetRepository;

    @Mock
    private UserRepository userRepository;

    private UserServiceImpl userService;
    private TweetServiceImpl service;

    private User testUser;

    @BeforeEach
    void setupClass() {
        testUser = new User(1L, "Sirma Atak", Gender.FEMALE, "sirma@atak.com","123");

        userService = new UserServiceImpl(userRepository, null, null);

        when(userService.getCurrentUser()).thenReturn(testUser);
        service = new TweetServiceImpl(tweetRepository, userService);
    }

    @Test
    void findByUserIdTest() {
        // Setup
        testUser.setId(1L);
        Tweet testTweet = new Tweet(1L, "Test tweet", LocalDate.now(), LocalDate.now(), testUser);
        when(tweetRepository.findByUserId(1L)).thenReturn(List.of(testTweet));

        // Execute
        List<Tweet> tweets = service.findByUserId(1L);

        // Assert
        assertEquals(1, tweets.size());
        Tweet retrievedTweet = tweets.get(0);
        assertEquals(testUser, retrievedTweet.getUser());
        assertEquals(testTweet, retrievedTweet);
    }
}
