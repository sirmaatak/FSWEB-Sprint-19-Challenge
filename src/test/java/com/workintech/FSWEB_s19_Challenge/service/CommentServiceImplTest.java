package com.workintech.FSWEB_s19_Challenge.service;

import com.workintech.FSWEB_s19_Challenge.dto.CommentRequest;
import com.workintech.FSWEB_s19_Challenge.entity.Comment;
import com.workintech.FSWEB_s19_Challenge.entity.Tweet;
import com.workintech.FSWEB_s19_Challenge.entity.User;
import com.workintech.FSWEB_s19_Challenge.exception.CustomException;
import com.workintech.FSWEB_s19_Challenge.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private TweetService tweetService;

    @Mock
    private UserService userService;

    @Test
    void create() {

        Long tweetId = 1L;

        User user = new User();
        user.setId(2L);

        Tweet tweet = new Tweet();
        tweet.setId(tweetId);

        CommentRequest request = new CommentRequest();
        request.setContent("Test comment");

        Comment savedComment = new Comment();
        savedComment.setId(10L);
        savedComment.setContent(request.getContent());
        savedComment.setUser(user);
        savedComment.setTweet(tweet);

        when(userService.getCurrentUser()).thenReturn(user);
        when(tweetService.findById(tweetId)).thenReturn(tweet);
        when(commentRepository.save(any(Comment.class))).thenReturn(savedComment);

        Comment result = commentService.create(tweetId, request);

        assertNotNull(result);
        assertEquals("Test comment", result.getContent());
        assertEquals(user, result.getUser());
        assertEquals(tweet, result.getTweet());

        verify(commentRepository).save(any(Comment.class));
    }

    //Yorum sahibi guncellemek isterse
    @Test
    void update1() {

        Long commentId = 1L;

        User user = new User();
        user.setId(1L);

        Comment comment = new Comment();
        comment.setId(commentId);
        comment.setUser(user);
        comment.setContent("Old content");

        CommentRequest request = new CommentRequest();
        request.setContent("Updated content");

        when(userService.getCurrentUser()).thenReturn(user);
        when(commentRepository.findById(commentId))
                .thenReturn(Optional.of(comment));
        when(commentRepository.save(comment)).thenReturn(comment);

        Comment result = commentService.update(commentId, request);

        assertEquals("Updated content", result.getContent());
        verify(commentRepository).save(comment);
    }

    //Yorum sahibi olmayan bir kullanici yorumu guncellemek isterse
    @Test
    void update2() {

        Long commentId = 1L;

        when(userService.getCurrentUser()).thenReturn(new User());
        when(commentRepository.findById(commentId))
                .thenReturn(Optional.empty());


        CustomException exception = assertThrows(
                CustomException.class,
                () -> commentService.update(commentId, new CommentRequest())
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    //Yorumu yorum sahibi silmek isterse
    @Test
    void delete1() {

        Long commentId = 1L;

        User user1 = new User();
        user1.setId(2L);

        User user2 = new User();
        user2.setId(3L);

        Tweet tweet = new Tweet();
        tweet.setUser(user2);

        Comment comment = new Comment();
        comment.setId(commentId);
        comment.setUser(user1);
        comment.setTweet(tweet);

        when(userService.getCurrentUser()).thenReturn(user1);
        when(commentRepository.findById(commentId))
                .thenReturn(Optional.of(comment));


        commentService.delete(commentId);


        verify(commentRepository).delete(comment);
    }

    //Yorumu tweet sahibi silmek isterse
    @Test
    void delete2() {
        // GIVEN
        Long commentId = 1L;

        User tweetOwner = new User();
        tweetOwner.setId(1L);

        User commentOwner = new User();
        commentOwner.setId(2L);

        Tweet tweet = new Tweet();
        tweet.setUser(tweetOwner);

        Comment comment = new Comment();
        comment.setUser(commentOwner);
        comment.setTweet(tweet);

        when(userService.getCurrentUser()).thenReturn(tweetOwner);
        when(commentRepository.findById(commentId))
                .thenReturn(Optional.of(comment));

        // WHEN
        commentService.delete(commentId);

        // THEN
        verify(commentRepository).delete(comment);
    }

}
