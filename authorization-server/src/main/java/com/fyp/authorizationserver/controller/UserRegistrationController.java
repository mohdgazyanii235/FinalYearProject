package com.fyp.authorizationserver.controller;

import com.fyp.authorizationserver.data.UserRegistrationDTO;
import com.fyp.authorizationserver.exception.AlreadyExistsException;
import com.fyp.authorizationserver.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controller for handling user registration requests.
 * <p>
 * This controller provides API endpoints for user registration within the system.
 * It relies on the {@link CustomUserDetailsService} to handle the actual registration process.
 * </p>
 */
@RestController
@AllArgsConstructor
public class UserRegistrationController {

    private final CustomUserDetailsService userDetailsService;


    /**
     * Registers a new user in the system.
     * <p>
     * This method handles HTTP POST requests for user registration, accepting user details
     * encapsulated within a {@link UserRegistrationDTO} object. If the user already exists,
     * it throws an {@link AlreadyExistsException} and returns an appropriate response.
     * </p>
     *
     * @param userRegistrationDTO The user registration details wrapped in {@link UserRegistrationDTO}.
     * @return A {@link ResponseEntity} indicating the outcome of the registration attempt.
     *         Returns an HTTP 200 OK response on successful registration or HTTP 400 Bad Request
     *         if the username already exists.
     */
    @PostMapping(path = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> register(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        try {
            this.userDetailsService.registerNewUser(userRegistrationDTO);
            return ResponseEntity.ok().build();
        } catch (AlreadyExistsException e) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
    }

}
