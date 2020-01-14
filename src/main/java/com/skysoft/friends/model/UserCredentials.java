package com.skysoft.friends.model;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import java.util.Random;

@Entity
@NoArgsConstructor
public class UserCredentials extends BaseEntity {

    @MapsId
    @OneToOne
    private UserEntity userEntity;

    private String password;

    private Integer confirmationCode;

    private boolean confirmed;

    public UserCredentials(UserEntity userEntity, String password) {
        this.userEntity = userEntity;
        this.password = password;
        this.confirmationCode = createRandomConfirmationCode();
    }

    private Integer createRandomConfirmationCode() {
        return new Random().ints(1_000_000, 9_999_999)
                .findFirst().orElse(3_321_526);
    }
}
