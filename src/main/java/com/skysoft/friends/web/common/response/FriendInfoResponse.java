package com.skysoft.friends.web.common.response;

import com.skysoft.friends.bussines.common.UserInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FriendInfoResponse {

    private String email;

    private String userName;

    private String firstName;

    private String lastName;

    private String address;

    private String phoneNumber;

    private FriendInfoResponse(String email, String userName, String firstName, String lastName, String address, String phoneNumber) {
        this.email = email;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public static FriendInfoResponse fromUserInfo(UserInfo userInfo) {
        return new FriendInfoResponse(userInfo.getEmail(), userInfo.getUserName(), userInfo.getFirstName(),
                userInfo.getLastName(), userInfo.getAddress(), userInfo.getPhoneNumber());
    }
}
