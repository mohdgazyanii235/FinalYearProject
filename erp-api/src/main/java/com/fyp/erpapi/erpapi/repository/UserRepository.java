package com.fyp.erpapi.erpapi.repository;

import com.fyp.erpapi.erpapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserByEmail(String email);


    @Query("SELECT u.isOnboardingComplete FROM User u WHERE u.email = ?1")
    Boolean isOnboardingCompleteByEmail(String email);
}
