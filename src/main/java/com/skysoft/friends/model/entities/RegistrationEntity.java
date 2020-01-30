package com.skysoft.friends.model.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RegistrationEntity extends BaseEntity {

    private static final long TOKEN_EXPIRATION_SECONDS = 5;

    private String userName;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String address;

    private String phoneNumber;

    private boolean confirmed;

    private String verificationToken;

    private Instant expireDate;

    public RegistrationEntity(String userName, String email, String password, String firstName,
                              String lastName, String address, String phoneNumber) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.verificationToken = UUID.randomUUID().toString();
        this.expireDate = generateExpirationDate();
    }

    private Instant generateExpirationDate() {
        return Instant.now().plus(TOKEN_EXPIRATION_SECONDS, ChronoUnit.SECONDS);
    }

    public void confirmRegistration() {
        confirmed = true;
    }

    public UserEntity toUserEntity() {
        return new UserEntity(userName, email, firstName, lastName, address, phoneNumber, password);
    }
}
