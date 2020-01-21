package com.skysoft.friends.bussines.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    private String userName;

    private String email;

    private String firstName;

    private String lastName;

    private String address;

    private String phoneNumber;
}
