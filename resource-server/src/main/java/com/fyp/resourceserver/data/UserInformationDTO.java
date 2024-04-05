package com.fyp.resourceserver.data;

import jakarta.persistence.GeneratedValue;
import lombok.*;


/**
 * Data Transfer Object (DTO) for user information.
 * <p>
 * This class is used to transfer user data between different layers of the application,
 * especially for responses that include user information like email, first name, last name,
 * and profile picture URL. It does not map directly to the database but may be constructed
 * from entities retrieved from the database.
 * </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserInformationDTO {

    private String email;
    private String firstName;
    private String lastName;
    private String profilePicture;
}
