package com.skysoft.friends.bussines.api;

import com.skysoft.friends.bussines.common.InvitationInfo;

import java.util.List;

public interface InvitationService {

    void sendInvitation(String requestedUserName, String potentialFriendUserName);

    void acceptInvitation(String acceptingInvitationUserLoginParameter, String invitationSenderUserName);

    void rejectInvitation(String currentUserLoginParameter, String invitationSenderUserName);

    void cancelInvitation(String currentUserLoginParameter, String invitationTargetUserName);

    List<InvitationInfo> getAllInBoxInvitations(String userLoginParameter);

    List<InvitationInfo> getAllOutGoingInvitations(String currentUserLoginParameter);

}
