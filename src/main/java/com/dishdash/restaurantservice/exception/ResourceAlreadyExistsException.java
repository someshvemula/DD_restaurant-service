package com.dishdash.restaurantservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceAlreadyExistsException extends RuntimeException{

    private String resourceName;
    private String fieldName;
    private long fieldValue;

    public ResourceAlreadyExistsException(String resourceName, String fieldName, long fieldValue) {
        super(String.format("%S already exists with %s: %s", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
