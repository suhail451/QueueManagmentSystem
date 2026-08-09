package com.QMS.QueueManagment.exception;

/**
 * Thrown when a requested resource (Queue, Token, Admin, etc.) does not exist.
 * Maps to HTTP 404.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
