package com.skysoft.friends.bussines.api;

import com.skysoft.friends.bussines.common.AllFriendsInfo;
import com.skysoft.friends.bussines.common.AllInvitedUsersInfo;

public interface FriendsService {

    AllFriendsInfo getFriendsInfo(String currentUserName);

    AllInvitedUsersInfo getInvitedUsersInfo(String currentUserName);

    void deleteFromFriends(String currentUserName, String targetUserName);
}
