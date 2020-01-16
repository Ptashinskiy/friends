package com.skysoft.friends.bussines.common;

import com.skysoft.friends.web.common.request.ConfirmRegistrationRequest;
import lombok.Data;

@Data
public class ConfirmationParameters {

    private String email;

    private Integer confirmationCode;

    private ConfirmationParameters(String email, Integer confirmationCode) {
        this.email = email;
        this.confirmationCode = confirmationCode;
    }

    public static ConfirmationParameters fromRequest(ConfirmRegistrationRequest request) {
        return new ConfirmationParameters(request.getEmail(), request.getConfirmationCode());
    }
}
