package com.workintech.FSWEB_s19_Challenge.service;

import com.workintech.FSWEB_s19_Challenge.dto.TweetRequest;
import com.workintech.FSWEB_s19_Challenge.entity.Tweet;
import com.workintech.FSWEB_s19_Challenge.entity.User;
import com.workintech.FSWEB_s19_Challenge.repository.TweetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TweetServiceImplTest {

    @InjectMocks
    private TweetServiceImpl tweetService;

    @Mock
    private TweetRepository tweetRepository;

    @Mock
    private UserService userService;

    @Test
    void findByUserId_shouldReturnTweets_whenUserIsAuthorized() {
        // GIVEN
        Long userId = 1L;

        User user = new User();
        user.setId(userId);

        Tweet tweet1 = new Tweet();
        Tweet tweet2 = new Tweet();

        List<Tweet> tweets = List.of(tweet1, tweet2);

        when(userService.getCurrentUser()).thenReturn(user);
        when(tweetRepository.findByUserId(userId)).thenReturn(tweets);

        // WHEN
        List<Tweet> result = tweetService.findByUserId(userId);

        // THEN
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(tweetRepository).findByUserId(userId);
    }

    @Test
    void findById_shouldReturnTweet(){

        Long tweetId=1L;

        Tweet testTweet=new Tweet();

        when(tweetRepository.findById(tweetId)).thenReturn(Optional.of(testTweet));

        Tweet tweet=tweetService.findById(tweetId);

        assertNotNull(tweet);
        assertEquals(testTweet,tweet);

    }

    @Test
    void create_shouldSaveAndReturnTweet_whenRequestIsValid() {
        // GIVEN
        TweetRequest tweetRequest = new TweetRequest();
        tweetRequest.setContent("Test tweet content");

        User user = new User();
        user.setId(1L);

        Tweet savedTweet = new Tweet();
        savedTweet.setId(10L);
        savedTweet.setContent(tweetRequest.getContent());
        savedTweet.setUser(user);

        when(userService.getCurrentUser()).thenReturn(user);
        when(tweetRepository.save(any(Tweet.class))).thenReturn(savedTweet);

        // WHEN
        Tweet result = tweetService.create(tweetRequest);

        // THEN
        assertNotNull(result);
        assertEquals("Test tweet content", result.getContent());
        assertEquals(user, result.getUser());

        verify(userService).getCurrentUser();
        verify(tweetRepository).save(any(Tweet.class));
    }

    @Test
    void updateTweet() {
        Long tweetId=1L;
        Tweet testTweet=new Tweet();
        User user = new User();
        user.setId(1L);
        testTweet.setUser(user);
        when(tweetRepository.findById(tweetId)).thenReturn(Optional.of(testTweet));
        when(tweetRepository.save(any(Tweet.class))).thenReturn(testTweet);
        when(userService.getCurrentUser()).thenReturn(user);


        Tweet result=tweetService.update(tweetId,new TweetRequest("Content has changed"));

        assertNotNull(result);
        assertEquals("Content has changed",result.getContent());

        verify(userService).getCurrentUser();
    }

    @Test
    void deleteTweet(){
        Long tweetId=1L;
        Tweet testTweet=new Tweet();
        User user = new User();
        user.setId(1L);
        testTweet.setUser(user);
        when(tweetRepository.findById(tweetId)).thenReturn(Optional.of(testTweet));
        when(userService.getCurrentUser()).thenReturn(user);

        tweetService.delete(tweetId);

        verify(tweetRepository).delete(testTweet);

    }

}

