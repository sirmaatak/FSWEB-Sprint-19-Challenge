package com.workintech.FSWEB_s19_Challenge.repository;

import com.workintech.FSWEB_s19_Challenge.entity.Retweet;
import com.workintech.FSWEB_s19_Challenge.entity.Tweet;
import com.workintech.FSWEB_s19_Challenge.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetweetRepository extends JpaRepository<Retweet,Long> {

    boolean existsByUserAndTweet(User user, Tweet tweet);

    Retweet findByUserAndTweet(User user, Tweet tweet);

}
