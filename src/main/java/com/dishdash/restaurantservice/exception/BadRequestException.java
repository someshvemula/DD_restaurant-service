package com.dishdash.restaurantservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException{
    private String paramName;
    private String paramValue;

    public BadRequestException(String paramName, String paramValue) {
        super(String.format("'%s' is not a valid %s", paramValue, paramName));
    }
}
