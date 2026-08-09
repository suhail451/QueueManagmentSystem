package com.QMS.QueueManagment.exception;

/**
 * Thrown when login fails — wrong username or wrong password.
 * Deliberately does not distinguish which one, to avoid leaking
 * whether a given username exists.
 * Maps to HTTP 401.
 */
public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
