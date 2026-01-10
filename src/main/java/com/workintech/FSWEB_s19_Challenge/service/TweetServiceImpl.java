package com.workintech.FSWEB_s19_Challenge.service;


import com.workintech.FSWEB_s19_Challenge.Entity.Tweet;
import com.workintech.FSWEB_s19_Challenge.Entity.User;
import com.workintech.FSWEB_s19_Challenge.dto.TweetRequest;
import com.workintech.FSWEB_s19_Challenge.exception.CustomException;
import com.workintech.FSWEB_s19_Challenge.repository.TweetRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;
    private final UserService userService;

    @Override
    @Transactional
    public Tweet create(TweetRequest tweetRequest) {

        User user = userService.getCurrentUser();

        Tweet tweet = new Tweet();
        tweet.setContent(tweetRequest.getContent());
        tweet.setCreateDate(LocalDate.now());
        tweet.setUpdateDate(LocalDate.now());
        tweet.setUser(user);

        return tweetRepository.save(tweet);
    }

    @Override
    @Transactional
    public Tweet update(Long id, TweetRequest tweetRequest) {

        Tweet tweet = tweetRepository.findById(id)
                .orElseThrow(() -> new CustomException(
                        "Tweet not found with id: " + id,
                        HttpStatus.NOT_FOUND
                ));

        if (tweet.getUser() == null ||
                !tweet.getUser().getId().equals(userService.getCurrentUser().getId())) {
            throw new CustomException(
                    "You are not authorized to update this tweet",
                    HttpStatus.FORBIDDEN
            );
        }

        tweet.setContent(tweetRequest.getContent());
        tweet.setUpdateDate(LocalDate.now());

        return tweetRepository.save(tweet);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        Tweet tweet = tweetRepository.findById(id)
                .orElseThrow(() -> new CustomException(
                        "Tweet not found with id: " + id,
                        HttpStatus.NOT_FOUND
                ));

        if (tweet.getUser() == null ||
                !tweet.getUser().getId().equals(userService.getCurrentUser().getId())) {
            throw new CustomException(
                    "You are not authorized to delete this tweet",
                    HttpStatus.FORBIDDEN
            );
        }

        tweetRepository.delete(tweet);
    }

    @Override
    public Tweet findById(Long id) {

        return tweetRepository.findById(id)
                .orElseThrow(() -> new CustomException(
                        "Tweet not found with id: " + id,
                        HttpStatus.NOT_FOUND
                ));
    }

    @Override
    public List<Tweet> findByUserId(Long userId) {

        if (!userService.getCurrentUser().getId().equals(userId)) {
            throw new CustomException(
                    "You are not authorized to view these tweets",
                    HttpStatus.FORBIDDEN
            );
        }

        return tweetRepository.findByUserId(userId);
    }
}
