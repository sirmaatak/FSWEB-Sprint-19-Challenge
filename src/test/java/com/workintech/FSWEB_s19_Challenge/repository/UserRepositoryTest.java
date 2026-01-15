package com.workintech.FSWEB_s19_Challenge.repository;

import com.workintech.FSWEB_s19_Challenge.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    //Kullanici bulunursa
    @Test
    void findByUserName1() {

        User user = new User();
        user.setFullName("Sirma Atak");
        user.setEmail("sirma@mail.com");
        user.setPassword("123456");
        userRepository.save(user);

        User result = userRepository.findByUserName("Sirma Atak");

        assertNotNull(result);
        assertEquals("Sirma Atak", result.getFullName());
        assertEquals("sirma@mail.com", result.getEmail());
    }

    //Kullanici bulunursa
    @Test
    void findByEmail1() {

        User user = new User();
        user.setFullName("Test User");
        user.setEmail("test@mail.com");
        user.setPassword("123456");

        userRepository.save(user);

        Optional<User> result = userRepository.findByEmail("test@mail.com");

        assertTrue(result.isPresent());
        assertEquals("test@mail.com", result.get().getEmail());
    }

}
