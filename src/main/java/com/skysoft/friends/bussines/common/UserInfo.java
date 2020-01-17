package com.skysoft.friends.bussines.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    private String email;

    private String userName;

    private String address;

    private String phoneNumber;
}
