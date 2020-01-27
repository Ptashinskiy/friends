package com.skysoft.friends.bussines.api;

import com.skysoft.friends.bussines.common.UserInfo;

import java.util.List;

public interface FriendsService {

    List<UserInfo> getFriendsInfo(String currentUserLoginParameter);

    List<UserInfo> getInvitedUsersInfo(String currentUserLoginParameter);

    void deleteFromFriends(String currentUserLoginParameter, String targetUserName);
}
