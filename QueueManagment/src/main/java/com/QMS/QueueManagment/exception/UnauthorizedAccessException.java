package com.QMS.QueueManagment.exception;

/**
 * Thrown when a logged-in admin tries to act on a resource they don't own
 * (e.g. Admin A trying to close Admin B's queue).
 * Maps to HTTP 403.
 */
public class UnauthorizedAccessException extends RuntimeException {

    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
