package com.workintech.FSWEB_s19_Challenge.repository;

import com.workintech.FSWEB_s19_Challenge.entity.Retweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetweetRepository extends JpaRepository<Retweet,Long> {

    Retweet findByUserIdAndTweetId(Long userId,Long tweetId);

}
