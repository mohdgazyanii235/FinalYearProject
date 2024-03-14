package com.fyp.authorizationserver.controller;

import com.fyp.authorizationserver.data.UserRegistrationDTO;
import com.fyp.authorizationserver.exception.AlreadyExistsException;
import com.fyp.authorizationserver.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserRegistrationController {

    private final CustomUserDetailsService userDetailsService;

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
