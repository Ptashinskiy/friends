package com.skysoft.friends.model.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InvitationEntity extends BaseEntity {

    @ManyToOne
    private UserEntity invitationTarget;

    @ManyToOne
    private UserEntity invitationSender;

    @Enumerated(EnumType.STRING)
    private InvitationStatus status;

    public InvitationEntity(UserEntity invitationTarget, UserEntity invitationSender) {
        this.invitationTarget = invitationTarget;
        this.invitationSender = invitationSender;
        this.status = InvitationStatus.PENDING;
    }

    public void updateStatus(InvitationStatus status) {
        this.status = status;
    }
}
