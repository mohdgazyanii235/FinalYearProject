package com.fyp.resourceserver.repository;

import com.fyp.resourceserver.entity.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for {@link UserInformation} entities.
 * <p>
 * Extends {@link JpaRepository} to provide standard CRUD operations on {@link UserInformation}
 * entities. Includes custom functionality to retrieve user information by email.
 * </p>
 */
@Repository
public interface UserInformationRepository extends JpaRepository<UserInformation, Long> {

    /**
     * Retrieves an {@link Optional} of {@link UserInformation} by the user's email.
     * <p>
     * This method is used to find user information entities based on the email address,
     * potentially for use in authentication, authorization, or user profile display.
     * </p>
     *
     * @param email The email address associated with the user information to be retrieved.
     * @return An {@link Optional} containing the {@link UserInformation} if found, or an empty
     *         {@link Optional} if no user information exists for the provided email.
     */
    Optional<UserInformation> getUserInformationByEmail(String email);

}
