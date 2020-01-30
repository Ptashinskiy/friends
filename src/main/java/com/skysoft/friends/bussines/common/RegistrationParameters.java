package com.skysoft.friends.bussines.common;

import com.skysoft.friends.model.entities.RegistrationEntity;
import com.skysoft.friends.web.common.request.RegistrationRequest;
import lombok.Data;

@Data
public class RegistrationParameters {

    private String userName;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String address;

    private String phoneNumber;

    private RegistrationParameters(String userName, String email, String password, String firstName, String lastName, String address, String phoneNumber) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public static RegistrationParameters fromRequest(RegistrationRequest request) {
        return new RegistrationParameters(request.getUserName(), request.getEmail(), request.getPassword(), request.getFirstName(),
                request.getLastName(), request.getAddress(), request.getPhoneNumber());
    }

    public RegistrationEntity toEntity() {
        return new RegistrationEntity(userName, email, password, firstName, lastName, address, phoneNumber);
    }
}
