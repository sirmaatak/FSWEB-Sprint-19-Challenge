package com.workintech.FSWEB_s19_Challenge.repository;

import com.workintech.FSWEB_s19_Challenge.entity.Tweet;
import com.workintech.FSWEB_s19_Challenge.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TweetRepositoryTest {

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void findByUserId() {

        User user1 = new User();
        user1.setFullName("User One");
        user1.setEmail("user1@mail.com");
        user1.setPassword("password");
        entityManager.persist(user1);

        User user2 = new User();
        user2.setFullName("User Two");
        user2.setEmail("user2@mail.com");
        user2.setPassword("password");
        entityManager.persist(user2);

        Tweet tweet1 = new Tweet();
        tweet1.setContent("Tweet 1");
        tweet1.setCreateDate(LocalDate.now());
        tweet1.setUpdateDate(LocalDate.now());
        tweet1.setUser(user1);
        entityManager.persist(tweet1);

        Tweet tweet2 = new Tweet();
        tweet2.setContent("Tweet 2");
        tweet2.setCreateDate(LocalDate.now());
        tweet2.setUpdateDate(LocalDate.now());
        tweet2.setUser(user1);
        entityManager.persist(tweet2);

        Tweet tweet3 = new Tweet();
        tweet3.setContent("Tweet 3");
        tweet3.setCreateDate(LocalDate.now());
        tweet3.setUpdateDate(LocalDate.now());
        tweet3.setUser(user2);
        entityManager.persist(tweet3);

        entityManager.flush();


        List<Tweet> result = tweetRepository.findByUserId(user1.getId());


        assertThat(result).hasSize(2);
        assertThat(result)
                .extracting(Tweet::getContent)
                .containsExactlyInAnyOrder("Tweet 1", "Tweet 2");
    }
}

