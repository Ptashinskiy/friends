package com.skysoft.friends.bussines.common;

import com.skysoft.friends.web.common.request.RegistrationRequest;
import lombok.Data;

@Data
public class RegistrationParameters {

    private String userName;

    private String email;

    private String password;

    private RegistrationParameters(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public static RegistrationParameters fromRequest(RegistrationRequest request) {
        return new RegistrationParameters(request.getUserName(), request.getEmail(), request.getPassword());
    }
}
