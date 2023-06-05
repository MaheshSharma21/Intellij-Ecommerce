package com.bikkadit.electronic.store.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super("Resource not found with given Id");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
