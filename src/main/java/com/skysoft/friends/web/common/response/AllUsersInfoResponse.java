package com.skysoft.friends.web.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllUsersInfoResponse {

    private List<UserInfoResponse> usersInfo;
}
