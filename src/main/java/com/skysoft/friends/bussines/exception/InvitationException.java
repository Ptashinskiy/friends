package com.skysoft.friends.bussines.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvitationException extends RuntimeException {

    private InvitationException(String message) {
        super(message);
    }

    public static InvitationException invitationTransmittedEarly() {
        return new InvitationException("You already transmit invitation to this user.");
    }

    public static InvitationException youHaveNoInvitationFromThisUser(String invitationSender) {
        return new InvitationException("You have no invitation from user " + invitationSender);
    }

    public static InvitationException youHaveNoInvitationToThisUser(String invitationTarget) {
        return new InvitationException("You have no invitation to user " + invitationTarget);
    }

    public static InvitationException selfInviting() {
        return new InvitationException("You can't send invitation to yourself.");
    }

    public static InvitationException reverseInvitation(String name) {
        return new InvitationException("You already have invitation from " + name + ". You can just accept it.");
    }
}
