package com.mishamba.project.exception;

public class CustomServiceException extends Exception {
    public CustomServiceException(String message) {
        super(message);
    }

    public CustomServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomServiceException(Throwable cause) {
        super(cause);
    }

    public CustomServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
