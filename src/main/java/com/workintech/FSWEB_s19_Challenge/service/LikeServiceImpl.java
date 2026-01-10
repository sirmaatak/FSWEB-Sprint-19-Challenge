package com.workintech.FSWEB_s19_Challenge.service;

import com.workintech.FSWEB_s19_Challenge.Entity.Like;
import com.workintech.FSWEB_s19_Challenge.Entity.Tweet;
import com.workintech.FSWEB_s19_Challenge.Entity.User;
import com.workintech.FSWEB_s19_Challenge.exception.CustomException;
import com.workintech.FSWEB_s19_Challenge.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService{

    private final LikeRepository likeRepository;
    private final TweetService tweetService;
    private final UserService userService;


    @Override
    public void like(Long tweetId) {
        User currentUser = userService.getCurrentUser();
        Tweet tweet = tweetService.findById(tweetId);

        //Burada tweete daha once like atildi mi kontrol ediyorum.
        //bir tweete bir kullanici yalniz bir kere like atabilir.
        if (likeRepository.existsByUserIdAndTweetId(currentUser.getId(), tweetId)) {
            throw new CustomException(
                    "User already liked this tweet",
                    HttpStatus.BAD_REQUEST
            );
        }
        Like like = new Like();
        like.setUser(currentUser);
        like.setTweet(tweet);

        likeRepository.save(like);
    }

    @Override
    public void dislike(Long tweetId) {

        User currentUser = userService.getCurrentUser();

        Like like = likeRepository
                .findByUserIdAndTweetId(currentUser.getId(), tweetId)
                .orElseThrow(() -> new CustomException(
                                "Like not found",
                                HttpStatus.NOT_FOUND
                        ));
        //dislike yaptigi icin sildim.
        likeRepository.delete(like);
    }
}
