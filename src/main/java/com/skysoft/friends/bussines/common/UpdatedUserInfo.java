package com.skysoft.friends.bussines.common;

import lombok.Data;

@Data
public class UpdatedUserInfo {

    private String userName;

    private String email;

    private String address;

    private String phoneNumber;

    private boolean emailChanged;

    private boolean userNameChanged;

    public boolean isEmailChanged() {
        return emailChanged;
    }

    public boolean isUserNameChanged() {
        return userNameChanged;
    }
}
