package com.skysoft.friends.web.common.response;

import com.skysoft.friends.bussines.common.UserInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InvitedFriendInfoResponse {

    private String userName;

    private String firstName;

    private String lastName;

    private InvitedFriendInfoResponse(String userName, String firstName, String lastName) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static InvitedFriendInfoResponse fromUserInfo(UserInfo userInfo) {
        return new InvitedFriendInfoResponse(userInfo.getUserName(), userInfo.getFirstName(), userInfo.getLastName());
    }
}
