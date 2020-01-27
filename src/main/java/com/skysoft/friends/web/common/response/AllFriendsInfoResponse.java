package com.skysoft.friends.web.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllFriendsInfoResponse {

    private List<FriendInfoResponse> friendsInfo;

}
