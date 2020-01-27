package com.skysoft.friends.bussines.api;

import com.skysoft.friends.bussines.common.InvitationInfo;
import com.skysoft.friends.bussines.common.UserInfo;

import java.util.List;

public interface FriendsService {

    void sendInvitationToBeFriends(String requestedUserName, String potentialFriendUserName);

    void acceptInvitation(String acceptingInvitationUserLoginParameter, String invitationSenderUserName);

    List<InvitationInfo> getAllInBoxInvitationsByUserLoginParameter(String userLoginParameter);

    void rejectInvitation(String currentUserLoginParameter, String invitationSenderUserName);

    void cancelInvitation(String currentUserLoginParameter, String invitationTargetUserName);

    List<InvitationInfo> getAllOutGoingInvitationsByUserLoginParameter(String currentUserLoginParameter);

    List<UserInfo> getAllFriendsInfo(String currentUserLoginParameter);

    List<UserInfo> getAllInvitedFriendsInfo(String currentUserLoginParameter);

    void deleteFromFriends(String currentUserLoginParameter, String targetUserName);
}
