package com.QMS.QueueManagment.exception;

/**
 * Thrown when an action conflicts with the current state of a resource
 * (e.g. creating a second queue for an admin who already has one,
 * closing a queue that's already closed, joining a closed queue).
 * Maps to HTTP 409.
 */
public class InvalidStateException extends RuntimeException {

    public InvalidStateException(String message) {
        super(message);
    }
}
