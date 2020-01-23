package com.skysoft.friends.bussines.common;

import com.skysoft.friends.model.entities.InvitationEntity;
import com.skysoft.friends.model.entities.InvitationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class InvitationInfo {

    private String invitationSender;

    private String invitationTarget;

    private InvitationStatus status;

    private Instant lastUpdatedTime;

    private InvitationInfo(String invitationSender, String invitationTarget, InvitationStatus status, Instant lastUpdatedTime) {
        this.invitationSender = invitationSender;
        this.invitationTarget = invitationTarget;
        this.status = status;
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public static InvitationInfo fromEntity(InvitationEntity invitationEntity) {
        return new InvitationInfo(invitationEntity.getInvitationSender().getUserName(), invitationEntity.getInvitationTarget().getUserName(),
                invitationEntity.getStatus(), invitationEntity.getLastUpdatedTime());
    }
}
