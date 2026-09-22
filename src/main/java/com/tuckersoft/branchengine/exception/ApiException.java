package com.tuckersoft.branchengine.exception;

public abstract class ApiException extends RuntimeException {

    protected ApiException(String message) {
        super(message);
    }
}