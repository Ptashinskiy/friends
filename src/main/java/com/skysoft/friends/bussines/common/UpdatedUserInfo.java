package com.skysoft.friends.bussines.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdatedUserInfo {

    private String firstName;

    private String lastName;

    private String address;

    private String phoneNumber;
}
