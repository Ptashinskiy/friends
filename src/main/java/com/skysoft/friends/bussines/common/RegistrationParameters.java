package com.skysoft.friends.bussines.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationParameters {

    private String userName;

    private String email;

    private String password;
}
