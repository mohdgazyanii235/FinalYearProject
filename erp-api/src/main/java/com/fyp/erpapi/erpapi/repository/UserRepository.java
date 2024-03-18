package com.fyp.erpapi.erpapi.repository;

import com.fyp.erpapi.erpapi.entity.User;
import com.fyp.erpapi.erpapi.enumeration.SSOIssuer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.email = ?1 and u.ssoIssuer = ?2")
    Optional<User> getRegisteredUserByEmailAndIssuer(String email, SSOIssuer ssoIssuer);

    Optional<User> getUserById(Long id);

    @Query("SELECT u.isOnboardingComplete FROM User u WHERE u.email = ?1")
    Boolean isOnboardingCompleteByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.firstName=?2 WHERE u.email=?1")
    void updateFirstName(String email, String firstName);

    @Modifying
    @Query("UPDATE User u SET u.lastName=?2 WHERE u.email=?1")
    void updateLastName(String email, String lastName);

    @Query("SELECT u.firstName FROM User u WHERE u.id = ?1")
    Optional<String> getFirstNameById(Long id);

    @Query("SELECT u.lastName FROM User u WHERE u.id = ?1")
    Optional<String> getLastNameById(Long id);

    @Query("SELECT u.firstName FROM User u WHERE u.email = ?1")
    Optional<String> getFirstNameByEmail(String email);

    @Query("SELECT u.lastName FROM User u WHERE u.email = ?1")
    Optional<String> getLastNameByEmail(String email);
}
