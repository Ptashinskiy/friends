package com.skysoft.friends.model;

import com.skysoft.friends.bussines.exception.UserException;
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

    public boolean isNotConfirmed() {
        return !confirmed;
    }

    public void confirmRegistration(Integer confirmationCode) {
        if (confirmationCode.equals(this.confirmationCode)) {
            confirmed = true;
        } else throw UserException.invalidConfirmationCode();
    }

    private Integer createRandomConfirmationCode() {
        return new Random().ints(1_000_000, 9_999_999)
                .findFirst().orElse(3_321_526);
    }
}
