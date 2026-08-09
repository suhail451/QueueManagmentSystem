package com.QMS.QueueManagment.exception;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Standard shape for every error response returned by the API,
 * so the frontend always knows what fields to expect regardless
 * of which exception was thrown.
 */
@Getter
public class ErrorResponse {

    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;

    public ErrorResponse(int status, String error, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
    }
}
