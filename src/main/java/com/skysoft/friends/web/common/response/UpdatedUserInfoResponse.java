package com.skysoft.friends.web.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatedUserInfoResponse {

    private String userName;

    private String email;

    private String address;

    private String phoneNumber;

}
