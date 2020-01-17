package com.skysoft.friends.model;

import com.skysoft.friends.bussines.common.UpdatedUserInfo;
import com.skysoft.friends.bussines.common.UserInfo;
import com.skysoft.friends.bussines.common.UserParametersToUpdate;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
public class UserEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false, unique = true)
    private String email;

    private String address;

    private String phoneNumber;

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

    public UpdatedUserInfo updateInfo(UserParametersToUpdate parametersToUpdate) {
        String newEmail = parametersToUpdate.getEmail();
        String newUserName = parametersToUpdate.getUserName();
        String newAddress = parametersToUpdate.getAddress();
        String newPhoneNumber = parametersToUpdate.getPhoneNumber();

        UpdatedUserInfo updatedUserInfo = new UpdatedUserInfo();

        if (newEmail != null) {
            this.setEmail(newEmail);
            updatedUserInfo.setEmail(newEmail);
            updatedUserInfo.setEmailChanged(true);
        }
        if (newUserName != null) {
            this.setUserName(newUserName);
            updatedUserInfo.setUserName(newUserName);
            updatedUserInfo.setUserNameChanged(true);
        }
        if (newAddress != null) {
            this.setAddress(newAddress);
            updatedUserInfo.setAddress(newAddress);
        }
        if (newPhoneNumber != null) {
            this.setPhoneNumber(newPhoneNumber);
            updatedUserInfo.setPhoneNumber(newPhoneNumber);
        }

        return updatedUserInfo;
    }

    public void dropEmailConfirmation() {
        credentials.dropConfirmation();
    }

    public UserInfo getUserInfo() {
        return new UserInfo(email, userName, address, phoneNumber);
    }
}
