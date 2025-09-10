package com.cams.authentication.exception;

/**
 * Custom exception to be thrown when a user attempts to sign up
 * with an email that is already in use.
 */
public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
