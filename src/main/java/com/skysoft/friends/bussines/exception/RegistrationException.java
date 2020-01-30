package com.skysoft.friends.bussines.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RegistrationException extends RuntimeException {

    private RegistrationException(String message) {
        super(message);
    }

    public static RegistrationException alreadyRegistered() {
        return new RegistrationException("Account with provided credentials already registered.");
    }

    public static RegistrationException alreadyConfirmed() {
        return new RegistrationException("This account already confirmed. Try to login.");
    }

    public static RegistrationException invalidVerificationToken() {
        return new RegistrationException("Your verification token is invalid. Try to register again.");
    }
}
