package com.mydigitalbank.api.domain.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String entityName, Long id) {
        super(String.format("%s not found with ID: %d", entityName, id));
    }
    public NotFoundException(String message) {
        super(message);
    }
}
