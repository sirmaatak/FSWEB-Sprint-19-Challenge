package com.workintech.FSWEB_s19_Challenge.service;


import com.workintech.FSWEB_s19_Challenge.entity.Retweet;
import com.workintech.FSWEB_s19_Challenge.entity.Tweet;
import com.workintech.FSWEB_s19_Challenge.entity.User;
import com.workintech.FSWEB_s19_Challenge.exception.CustomException;
import com.workintech.FSWEB_s19_Challenge.repository.RetweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RetweetServiceImpl implements RetweetService {

    private final RetweetRepository retweetRepository;
    private final TweetService tweetService;
    private final UserService userService;

    @Override
    public void retweet(Long tweetId) {

        User currentUser = userService.getCurrentUser();
        Tweet tweet = tweetService.findById(tweetId);

        if (retweetRepository.existsByUserAndTweet(currentUser, tweet)) {
            throw new CustomException(
                    "Tweet already retweeted",
                    HttpStatus.BAD_REQUEST
            );
        }

        Retweet retweet = new Retweet();
        retweet.setUser(currentUser);
        retweet.setTweet(tweet);
        retweet.setCreateDate(LocalDate.now());

        retweetRepository.save(retweet);
    }

    @Override
    public void undoRetweet(Long tweetId) {

        User currentUser = userService.getCurrentUser();
        Tweet tweet = tweetService.findById(tweetId);

        Retweet retweet =
                retweetRepository.findByUserAndTweet(currentUser, tweet);

        if (retweet == null) {
            throw new CustomException(
                    "Retweet not found",
                    HttpStatus.NOT_FOUND
            );
        }

        retweetRepository.delete(retweet);
    }
}

