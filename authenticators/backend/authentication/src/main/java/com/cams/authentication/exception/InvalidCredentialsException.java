package com.cams.authentication.exception;

/**
 * Custom exception to be thrown when a user provides incorrect
 * login credentials (e.g., wrong email or password).
 */
public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
