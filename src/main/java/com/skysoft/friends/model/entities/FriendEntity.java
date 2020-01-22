package com.skysoft.friends.model.entities;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FriendEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    private UserEntity friendOwner;

    @ManyToOne(fetch = FetchType.EAGER)
    private UserEntity friend;

    public FriendEntity(UserEntity friendOwner, UserEntity friend) {
        this.friendOwner = friendOwner;
        this.friend = friend;
    }


}
