package com.skysoft.friends.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Data
@Entity
@NoArgsConstructor
public class UserEntity extends BaseEntity {

    private String userName;

    private String email;

    @OneToOne(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private UserCredentials credentials;

    public void confirmRegistration(Integer confirmationCode) {
        credentials.confirmRegistration(confirmationCode);
    }

    public boolean isNotConfirmed() {
        return credentials.isNotConfirmed();
    }

    public UserEntity(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.credentials = new UserCredentials(this, password);
    }
}
