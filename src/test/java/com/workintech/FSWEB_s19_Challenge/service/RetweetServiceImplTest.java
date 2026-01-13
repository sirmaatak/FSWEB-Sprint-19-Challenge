package com.workintech.FSWEB_s19_Challenge.service;

import com.workintech.FSWEB_s19_Challenge.entity.Retweet;
import com.workintech.FSWEB_s19_Challenge.entity.Tweet;
import com.workintech.FSWEB_s19_Challenge.entity.User;
import com.workintech.FSWEB_s19_Challenge.repository.RetweetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class RetweetServiceImplTest {

    @InjectMocks
    private RetweetServiceImpl retweetServiceImpl;

    @Mock
    private  RetweetRepository retweetRepository;

    @Mock
    private  TweetService tweetService;

    @Mock
    private  UserService userService;

    @Test
    void retweet(){

        Long userId = 1L;
        Long tweetId=2L;
        User user = new User();
        user.setId(userId);

        Tweet tweet = new Tweet();
        when(userService.getCurrentUser()).thenReturn(user);
        when(tweetService.findById(tweetId)).thenReturn(tweet);

        retweetServiceImpl.retweet(tweetId);

        verify(retweetRepository).save(any(Retweet.class));
    }

    @Test
    void undoRetweet() {

        Long tweetId = 1L;

        User user = new User();
        user.setId(2L);

        Tweet tweet = new Tweet();
        tweet.setId(tweetId);

        Retweet retweet = new Retweet();
        retweet.setId(10L);
        retweet.setUser(user);
        retweet.setTweet(tweet);

        when(userService.getCurrentUser()).thenReturn(user);
        when(tweetService.findById(tweetId)).thenReturn(tweet);
        when(retweetRepository.findByUserAndTweet(user, tweet))
                .thenReturn(retweet);


        retweetServiceImpl.undoRetweet(tweetId);


        verify(retweetRepository).delete(retweet);
    }

}
