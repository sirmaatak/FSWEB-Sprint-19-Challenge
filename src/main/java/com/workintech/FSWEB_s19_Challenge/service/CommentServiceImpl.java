package com.workintech.FSWEB_s19_Challenge.service;

import com.workintech.FSWEB_s19_Challenge.entity.Comment;
import com.workintech.FSWEB_s19_Challenge.entity.Tweet;
import com.workintech.FSWEB_s19_Challenge.entity.User;
import com.workintech.FSWEB_s19_Challenge.dto.CommentRequest;
import com.workintech.FSWEB_s19_Challenge.exception.CustomException;
import com.workintech.FSWEB_s19_Challenge.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final TweetService tweetService;
    private final UserService userService;

    @Override
    @Transactional
    public Comment create(Long tweetId, CommentRequest commentRequest) {

        User currentUser = userService.getCurrentUser();

        //Burada extra tweet kontrolu yapmadim kendi servisi yapiyor.
        Tweet tweet = tweetService.findById(tweetId);

        Comment comment = new Comment();
        comment.setContent(commentRequest.getContent());
        comment.setTweet(tweet);
        comment.setUser(currentUser);

        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public Comment update(Long commentId, CommentRequest commentRequest) {

        User currentUser = userService.getCurrentUser();

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(
                        "Comment not found with id: " + commentId,
                        HttpStatus.NOT_FOUND
                ));

        if (!comment.getUser().getId().equals(currentUser.getId())) {
            throw new CustomException(
                    "You can only update your own comment",
                    HttpStatus.FORBIDDEN
            );
        }

        comment.setContent(commentRequest.getContent());
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void delete(Long commentId) {

        //Burada bir yorum silinirken
        // ya tweet sahibi yapilan yorumu silebilmeli
        // ya da yorumun sahibi kendi yorumunu silebilmeli

        User currentUser = userService.getCurrentUser();

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(
                        "Comment not found with id: " + commentId,
                        HttpStatus.NOT_FOUND
                ));

        boolean isCommentOwner =
                comment.getUser().getId().equals(currentUser.getId());

        boolean isTweetOwner =
                comment.getTweet().getUser().getId().equals(currentUser.getId());

        if (!isCommentOwner && !isTweetOwner) {
            throw new CustomException(
                    "You are not authorized to delete this comment",
                    HttpStatus.FORBIDDEN
            );
        }

        commentRepository.delete(comment);
    }
}
