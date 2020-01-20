package com.skysoft.friends.web.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {

    private String email;

    private String userName;

    private String firstName;

    private String lastName;

    private String address;

    private String phoneNumber;
}
