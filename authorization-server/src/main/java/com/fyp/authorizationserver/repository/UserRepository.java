package com.fyp.authorizationserver.repository;

import com.fyp.authorizationserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data JPA repository for {@link User} entities.
 * <p>
 * Provides the mechanism for storage, retrieval, update, and delete operations on {@link User} objects.
 * Includes custom methods for finding a user by email and checking if a user exists by email.
 * </p>
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    /**
     * Finds a user by their email address.
     *
     * @param email The email address to search for.
     * @return An {@link Optional} containing the found {@link User}, or an empty {@link Optional} if no user is found.
     */
    Optional<User> findUserByEmail(String email);


    /**
     * Checks if a user exists with the given email address.
     *
     * @param email The email address to search for.
     * @return {@code true} if a user exists with the given email address, otherwise {@code false}.
     */
    Boolean existsByEmail(String email);
}
