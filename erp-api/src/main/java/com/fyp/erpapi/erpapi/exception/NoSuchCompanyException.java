package com.fyp.erpapi.erpapi.exception;

/**
 * Exception thrown when a specified company is not found.
 * This could be used when trying to access or modify a company that does not exist in the database.
 */
public class NoSuchCompanyException extends Exception {
        public NoSuchCompanyException(String message) {
            super(message);
        }
}
