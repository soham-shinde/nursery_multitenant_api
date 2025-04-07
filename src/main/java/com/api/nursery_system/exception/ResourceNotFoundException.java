package com.api.nursery_system.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
          super(message);
    }
}