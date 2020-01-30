package com.skysoft.friends.bussines.api;

import com.skysoft.friends.bussines.common.AllInBoxInvitations;
import com.skysoft.friends.bussines.common.AllOutGoingInvitations;

public interface InvitationService {

    void sendInvitation(String invitationSenderUserName, String invitationTargetUserName);

    void acceptInvitation(String currentUserName, String invitationSenderUserName);

    void rejectInvitation(String currentUserName, String invitationSenderUserName);

    void cancelInvitation(String currentUserName, String invitationTargetUserName);

    AllInBoxInvitations getAllInBoxInvitations(String currentUserName);

    AllOutGoingInvitations getAllOutGoingInvitations(String currentUserName);
}
