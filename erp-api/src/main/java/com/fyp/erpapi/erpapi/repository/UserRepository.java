package com.fyp.erpapi.erpapi.repository;

import com.fyp.erpapi.erpapi.entity.User;
import com.fyp.erpapi.erpapi.enumeration.SSOIssuer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Repository interface for {@link User} entities.
 * Includes queries for user-specific operations.
 */
@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Retrieves a user by their email.
     *
     * @param email The email of the user.
     * @return An Optional containing the user if found, or an empty Optional if not.
     */
    Optional<User> getUserByEmail(String email);

    /**
     * Retrieves a registered user by their email and SSO issuer.
     *
     * @param email The email of the user.
     * @param ssoIssuer The Authorization Server by which the user registered.
     * @return An Optional containing the user if found, or an empty Optional if not.
     */
    @Query("SELECT u FROM User u WHERE u.email = ?1 and u.ssoIssuer = ?2")
    Optional<User> getRegisteredUserByEmailAndIssuer(String email, SSOIssuer ssoIssuer);

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user.
     * @return An Optional containing the user if found, or an empty Optional if not.
     */
    Optional<User> getUserById(Long id);

    /**
     * Checks if onboarding is complete for a user identified by email.
     *
     * @param email The email of the user.
     * @return True if onboarding is complete, false otherwise.
     */
    @Query("SELECT u.isOnboardingComplete FROM User u WHERE u.email = ?1")
    Boolean isOnboardingCompleteByEmail(String email);

    /**
     * Checks if a user exists by their email.
     *
     * @param email The email of the user.
     * @return True if a user exists with the given email, false otherwise.
     */
    Boolean existsByEmail(String email);

    /**
     * Updates the first name of a user identified by email.
     *
     * @param email The email of the user.
     * @param firstName The new first name to set.
     */
    @Modifying
    @Query("UPDATE User u SET u.firstName=?2 WHERE u.email=?1")
    void updateFirstName(String email, String firstName);

    /**
     * Updates the last name of a user identified by email.
     *
     * @param email The email of the user.
     * @param lastName The new last name to set.
     */
    @Modifying
    @Query("UPDATE User u SET u.lastName=?2 WHERE u.email=?1")
    void updateLastName(String email, String lastName);

    /**
     * Retrieves the first name of a user by their email.
     *
     * @param email The email of the user.
     * @return An Optional containing the first name if found, or an empty Optional if not.
     */
    @Query("SELECT u.firstName FROM User u WHERE u.email = ?1")
    Optional<String> getFirstNameByEmail(String email);

    /**
     * Retrieves the last name of a user by their email.
     *
     * @param email The email of the user.
     * @return An Optional containing the last name if found, or an empty Optional if not.
     */
    @Query("SELECT u.lastName FROM User u WHERE u.email = ?1")
    Optional<String> getLastNameByEmail(String email);
}
