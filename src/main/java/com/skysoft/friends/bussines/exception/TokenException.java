package com.skysoft.friends.bussines.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TokenException extends RuntimeException {

    private TokenException(String message) {
        super(message);
    }

    public static TokenException cantFindToken() {
        return new TokenException("Impossible to find token");
    }

    public static TokenException invalidAccessToken() {
        return new TokenException("Invalid access token.");
    }
}
