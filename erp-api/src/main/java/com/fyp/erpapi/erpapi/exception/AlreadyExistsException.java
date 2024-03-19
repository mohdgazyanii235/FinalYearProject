package com.fyp.erpapi.erpapi.exception;

/**
 * Exception thrown when an attempt is made to create an entity that already exists.
 * For example, trying to create a user in the system with an email that already exists.
 */
public class AlreadyExistsException extends Exception {
        public AlreadyExistsException(String message) {
            super(message);
        }
}
