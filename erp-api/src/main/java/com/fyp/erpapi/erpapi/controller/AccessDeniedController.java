package com.fyp.erpapi.erpapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controller for handling requests that result in access being denied.
 * This controller provides a centralized way to manage responses for access denied situations,
 * returning a standardized response to the client indicating that the requested action or resource
 * is forbidden.
 */
@RestController
@RequestMapping("/accessDenied")
public class AccessDeniedController {


    /**
     * Handles requests to the access denied endpoint.
     * This method is invoked when a user tries to access a resource or perform an action for which
     * they do not have the necessary permissions. It returns a 403 Forbidden status code along with
     * a simple message indicating that access is denied.
     *
     * @return A {@link ResponseEntity} encapsulating the status code and the access denied message.
     */
    @GetMapping
    public ResponseEntity<?> accessDenied() {
        return ResponseEntity.status(403).body("Access Denied");
    }

}
