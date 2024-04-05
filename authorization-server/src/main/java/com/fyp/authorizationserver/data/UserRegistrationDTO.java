package com.fyp.authorizationserver.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

/**
 * Data Transfer Object (DTO) for user registration.
 * <p>
 * This class encapsulates the necessary information for registering a new user,
 * including the user's email and password.
 * </p>
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRegistrationDTO {

    /**
     * The email of the user. This field is used as a unique identifier for the user
     * and must be a valid email format.
     */
    private String email;

    /**
     * The password of the user. It is recommended to store passwords in a securely hashed format.
     * This field will be used for authentication purposes.
     */
    private String password;

}
