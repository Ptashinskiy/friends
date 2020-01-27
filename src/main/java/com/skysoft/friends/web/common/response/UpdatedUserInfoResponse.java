package com.skysoft.friends.web.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.skysoft.friends.bussines.common.UpdatedUserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdatedUserInfoResponse {

    private String firstName;

    private String lastName;

    private String address;

    private String phoneNumber;

    private UpdatedUserInfoResponse(String firstName, String lastName, String address, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public static UpdatedUserInfoResponse fromUpdatedUserInfo(UpdatedUserInfo updatedUserInfo) {
        return new UpdatedUserInfoResponse(updatedUserInfo.getFirstName(), updatedUserInfo.getLastName(),
                updatedUserInfo.getAddress(), updatedUserInfo.getPhoneNumber());
    }
}
