package com.skysoft.friends.web.common.response;

import com.skysoft.friends.bussines.common.UserInfo;
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

    public static UserInfoResponse fromUserInfo(UserInfo userInfo) {
        return new UserInfoResponse(userInfo.getEmail(), userInfo.getUserName(), userInfo.getFirstName(),
                userInfo.getLastName(), userInfo.getAddress(), userInfo.getPhoneNumber());
    }
}
