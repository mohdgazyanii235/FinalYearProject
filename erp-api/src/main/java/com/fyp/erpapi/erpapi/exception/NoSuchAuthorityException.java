package com.fyp.erpapi.erpapi.exception;

/**
 * Exception thrown when a specified authority is not found.
 * This could occur if an operation is attempted on an authority that does not exist in the system.
 */
public class NoSuchAuthorityException extends Exception {
    public NoSuchAuthorityException(String message) {
        super(message);
    }
}
