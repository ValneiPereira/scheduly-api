package com.scheduly.api.domain.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceName, Long id) {
        super(String.format("%s não encontrado com ID: %d", resourceName, id));
    }

    public ResourceNotFoundException(String resourceName, String identifier) {
        super(String.format("%s não encontrado: %s", resourceName, identifier));
    }
}
