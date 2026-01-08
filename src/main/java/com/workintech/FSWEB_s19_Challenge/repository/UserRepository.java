package com.workintech.FSWEB_s19_Challenge.repository;

import com.workintech.FSWEB_s19_Challenge.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query(value="SELECT u FROM User u WHERE u.fullName=:fullName")
    User findByUserName(String fullName);

    @Query(value = "SELECT u FROM User u WHERE u.email=:email")
    User findByEmail(String email);
}
