package com.skysoft.friends.bussines.exception;

public class UserException extends RuntimeException {

    private UserException(String message) {
        super(message);
    }

    public static UserException userAlreadyExist(String email) {
        return new UserException("User with provided email " + email + " already exist.");
    }
}
