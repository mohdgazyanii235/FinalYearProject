package com.fyp.erpapi.erpapi.exception;

/**
 * Exception thrown when an unknown registration ID is encountered.
 * Typically occurs during OAuth2 or OpenID Connect authentication processes
 * when a client registration ID does not match any known configuration.
 */
public class UnknownRegistrationIdException extends RuntimeException{

    public UnknownRegistrationIdException(String message) {
        super(message);
    }
}
