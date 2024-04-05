package com.fyp.resourceserver.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Entity representing user information stored in the database.
 * <p>
 * This entity contains basic user information including first name, last name, email,
 * and a URL to the profile picture. It is annotated with JPA annotations to define how it is
 * stored and retrieved from the database.
 * </p>
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInformation {


    /**
     * The unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /**
     * The user's first name.
     */
    private String firstName;


    /**
     * The user's last name.
     */
    private String lastName;

    /**
     * The user's email address. Can be used as a unique identifier for the user.
     */
    private String email;

    /**
     * The URL to the user's profile picture.
     */
    private String profilePicture;

}
