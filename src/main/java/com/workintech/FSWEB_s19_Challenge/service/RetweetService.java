package com.workintech.FSWEB_s19_Challenge.service;

public interface RetweetService {
    void retweet(Long tweetId);
    void undoRetweet(Long tweetId);
}
