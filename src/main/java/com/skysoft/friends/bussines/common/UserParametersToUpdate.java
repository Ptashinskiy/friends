package com.skysoft.friends.bussines.common;

import com.skysoft.friends.web.common.request.UpdateUserInfoRequest;
import lombok.Data;

@Data
public class UserParametersToUpdate {

    private String address;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private UserParametersToUpdate(String address, String phoneNumber, String firstName, String lastName) {
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static UserParametersToUpdate fromRequest(UpdateUserInfoRequest request) {
        return new UserParametersToUpdate(request.getAddress(), request.getPhoneNumber(), request.getFirstName(), request.getLastName());
    }
}
