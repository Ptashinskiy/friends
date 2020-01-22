package com.skysoft.friends.model.entities;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InvitationEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    private UserEntity invitationTarget;

    @ManyToOne(fetch = FetchType.EAGER)
    private UserEntity invitationSender;

    public InvitationEntity(UserEntity invitationTarget, UserEntity invitationSender) {
        this.invitationTarget = invitationTarget;
        this.invitationSender = invitationSender;
    }

    public UserEntity getInvitationSender() {
        return invitationSender;
    }

}
