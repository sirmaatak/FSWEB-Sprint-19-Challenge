package com.workintech.FSWEB_s19_Challenge.service;

import com.workintech.FSWEB_s19_Challenge.entity.Like;
import com.workintech.FSWEB_s19_Challenge.entity.Tweet;
import com.workintech.FSWEB_s19_Challenge.entity.User;
import com.workintech.FSWEB_s19_Challenge.repository.LikeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class LikeServiceImplTest {

    @InjectMocks
    private LikeServiceImpl likeService;

    @Mock
    private TweetService tweetService;

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private UserService userService;

    @Test
    void like() {

        Long tweetId = 1L;

        User user = new User();
        user.setId(2L);

        Tweet tweet = new Tweet();
        tweet.setId(tweetId);

        when(userService.getCurrentUser()).thenReturn(user);
        when(tweetService.findById(tweetId)).thenReturn(tweet);
        when(likeRepository.existsByUserIdAndTweetId(user.getId(), tweetId))
                .thenReturn(false);


        likeService.like(tweetId);


        verify(likeRepository).save(any(Like.class));
    }

    @Test
    void dislike() {

        Long tweetId = 1L;

        User user = new User();
        user.setId(2L);

        Like like = new Like();
        like.setId(10L);

        when(userService.getCurrentUser()).thenReturn(user);
        when(likeRepository.findByUserIdAndTweetId(user.getId(), tweetId))
                .thenReturn(Optional.of(like));


        likeService.dislike(tweetId);


        verify(likeRepository).delete(like);
    }

}
