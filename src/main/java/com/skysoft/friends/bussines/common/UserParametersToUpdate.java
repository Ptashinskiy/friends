package com.skysoft.friends.bussines.common;

import com.skysoft.friends.web.common.request.UpdateUserInfoRequest;
import lombok.Data;

@Data
public class UserParametersToUpdate {

    private String userName;

    private String email;

    private String address;

    private String phoneNumber;

    private UserParametersToUpdate(String userName, String email, String address, String phoneNumber) {
        this.userName = userName;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public static UserParametersToUpdate fromRequest(UpdateUserInfoRequest request) {
        return new UserParametersToUpdate(request.getUserName(), request.getEmail(), request.getAddress(), request.getPhoneNumber());
    }
}
