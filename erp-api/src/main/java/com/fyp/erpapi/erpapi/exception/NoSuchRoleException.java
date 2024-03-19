package com.fyp.erpapi.erpapi.exception;

/**
 * Exception thrown when a specified role is not found.
 * This could happen if an operation requires a role that is not defined in the system.
 */
public class NoSuchRoleException extends Exception{

    public NoSuchRoleException(String message) {
        super(message);
    }
}
