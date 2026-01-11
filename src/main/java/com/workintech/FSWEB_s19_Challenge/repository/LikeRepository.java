package com.workintech.FSWEB_s19_Challenge.repository;

import com.workintech.FSWEB_s19_Challenge.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like,Long> {

    Optional<Like> findByUserIdAndTweetId(Long userId, Long tweetId);

    boolean existsByUserIdAndTweetId(Long userId, Long tweetId);
}
