package com.fyp.erpapi.erpapi.exception;

/**
 * Exception thrown when authentication fails.
 * This could be due to invalid credentials, expired sessions, or other authentication-related issues.
 */
public class AuthenticationException extends Exception {

    public AuthenticationException(String message) {
        super(message);
    }
}
