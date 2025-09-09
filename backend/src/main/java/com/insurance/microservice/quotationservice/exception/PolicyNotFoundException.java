package com.insurance.microservice.quotationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * A custom exception that is thrown when a policy is not found in the database.
 * The @ResponseStatus(HttpStatus.NOT_FOUND) annotation tells Spring Boot to
 * automatically return a 404 Not Found HTTP status code if this exception is thrown.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PolicyNotFoundException extends RuntimeException {
    public PolicyNotFoundException(String message) {
        super(message);
    }
}
