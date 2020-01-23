package com.skysoft.friends.bussines.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FriendsException extends RuntimeException {

    private FriendsException(String message) {
        super(message);
    }

    public static FriendsException youAlreadyFriendsWith(String friendsUserName) {
        return new FriendsException("You already friends with " + friendsUserName);
    }
}
