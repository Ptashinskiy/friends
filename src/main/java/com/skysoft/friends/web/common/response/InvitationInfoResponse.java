package com.skysoft.friends.web.common.response;

import com.skysoft.friends.bussines.common.InvitationInfo;
import com.skysoft.friends.model.entities.InvitationStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class InvitationInfoResponse {

    private String invitationSenderUserName;

    private String invitationTargetUserName;

    private Instant lastUpdatedTime;

    private InvitationStatus status;

    private InvitationInfoResponse(String invitationSenderUserName, String invitationTargetUserName, Instant lastUpdatedTime, InvitationStatus status) {
        this.invitationSenderUserName = invitationSenderUserName;
        this.invitationTargetUserName = invitationTargetUserName;
        this.lastUpdatedTime = lastUpdatedTime;
        this.status = status;
    }

    public static InvitationInfoResponse fromInvitationInfo(InvitationInfo invitationInfo) {
        return new InvitationInfoResponse(invitationInfo.getInvitationSender(), invitationInfo.getInvitationTarget(),
                invitationInfo.getLastUpdatedTime(), invitationInfo.getStatus());
    }
}
