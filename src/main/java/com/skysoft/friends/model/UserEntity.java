package com.skysoft.friends.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Objects;

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

    public boolean isConfirmed() {
        return credentials.isConfirmed();
    }

    public UserEntity(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.credentials = new UserCredentials(this, password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(userName, that.userName) &&
                Objects.equals(email, that.email) &&
                Objects.equals(credentials, that.credentials);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userName, email, credentials);
    }
}
