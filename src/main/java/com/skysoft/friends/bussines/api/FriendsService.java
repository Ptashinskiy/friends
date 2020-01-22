package com.skysoft.friends.bussines.api;

public interface FriendsService {

    void sendInvitationToBeFriends(String requestedUserName, String potentialFriendUserName);

    void acceptInvitation(String acceptingInvitationUserLoginParameter, String invitationSenderUserName);
}
