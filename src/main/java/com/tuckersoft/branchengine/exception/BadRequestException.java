package com.tuckersoft.branchengine.exception;

public class BadRequestException extends ApiException {

    public BadRequestException(String message) {
        super(message);
    }
}