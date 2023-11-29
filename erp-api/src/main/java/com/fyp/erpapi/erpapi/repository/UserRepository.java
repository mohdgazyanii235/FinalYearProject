package com.fyp.erpapi.erpapi.repository;

import com.fyp.erpapi.erpapi.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserByEmail(String email);

    Optional<User> getUserById(Long id);

    @Query("SELECT u.isOnboardingComplete FROM User u WHERE u.email = ?1")
    Boolean isOnboardingCompleteByEmail(String email);

    @Transactional
    @Query("UPDATE User u SET u.firstName=?2 WHERE u.id=?1")
    void updateFirstName(Long id, String firstName);

    @Transactional
    @Query("UPDATE User u SET u.lastName=?2 WHERE u.id=?1")
    void updateLastName(Long id, String lastName);

    @Query("SELECT u.firstName FROM User u WHERE u.id = ?1")
    Optional<String> getFirstNameById(Long id);

    @Query("SELECT u.lastName FROM User u WHERE u.id = ?1")
    Optional<String> getLastNameById(Long id);
}
