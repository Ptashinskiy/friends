package com.skysoft.friends.bussines.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    private NotFoundException(String message) {
        super(message);
    }

    public static NotFoundException userNotFound(String email) {
        return new NotFoundException("User with provided email " + email + " not found.");
    }
}
