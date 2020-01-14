package com.skysoft.friends.bussines.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserException extends RuntimeException {

    private UserException(String message) {
        super(message);
    }

    public static UserException userAlreadyExist(String email) {
        return new UserException("User with provided email " + email + " already exist.");
    }

    public static UserException confirmationNotRequired(String email) {
        return new UserException("User with provided email " + email + " already confirm it.");
    }

    public static UserException invalidConfirmationCode() {
        return new UserException("Invalid confirmation code.");
    }
}
