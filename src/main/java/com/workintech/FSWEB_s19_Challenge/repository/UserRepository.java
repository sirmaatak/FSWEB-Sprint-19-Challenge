package com.workintech.FSWEB_s19_Challenge.repository;

import com.workintech.FSWEB_s19_Challenge.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query(value="SELECT u FROM User u WHERE u.fullName=:fullName")
    User findByUserName(String fullName);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

}
