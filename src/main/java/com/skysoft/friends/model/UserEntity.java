package com.skysoft.friends.model;

import com.skysoft.friends.bussines.common.UpdatedUserInfo;
import com.skysoft.friends.bussines.common.UserInfo;
import com.skysoft.friends.bussines.common.UserParametersToUpdate;
import com.skysoft.friends.bussines.exception.UserException;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Objects;
import java.util.Random;

@Data
@Entity
@NoArgsConstructor
public class UserEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false, unique = true)
    private String email;

    private String firstName;

    private String lastName;

    private String address;

    private String phoneNumber;

    private String password;

    private Integer confirmationCode;

    private boolean emailConfirmed;

    public UserEntity(String userName, String email, String firstName, String lastName, String address,
                      String phoneNumber, String password) {
        this.userName = userName;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.confirmationCode = createRandomConfirmationCode();
    }

    public boolean isEmailNotConfirmed() {
        return !isEmailConfirmed();
    }

    public boolean isEmailConfirmed() {
        return emailConfirmed;
    }

    public void confirmRegistration(Integer confirmationCode) {
        if (confirmationCode.equals(this.confirmationCode)) {
            emailConfirmed = true;
        } else throw UserException.invalidConfirmationCode();
    }

    private Integer createRandomConfirmationCode() {
        return new Random().ints(1_000_000, 9_999_999)
                .findFirst().orElse(3_321_526);
    }

    public UserInfo getUserInfo() {
        return new UserInfo(userName, email, firstName, lastName, address, phoneNumber);
    }

    public UpdatedUserInfo updateInfo(UserParametersToUpdate parametersToUpdate) {
        String newAddress = parametersToUpdate.getAddress();
        String newPhoneNumber = parametersToUpdate.getPhoneNumber();
        String newFirstName = parametersToUpdate.getFirstName();
        String newLastName = parametersToUpdate.getLastName();

        if (newFirstName != null) {
            this.setFirstName(newFirstName);
        }
        if (newLastName != null) {
            this.setLastName(newLastName);
        }
        if (newAddress != null) {
            this.setAddress(newAddress);
        }
        if (newPhoneNumber != null) {
            this.setPhoneNumber(newPhoneNumber);
        }

        return new UpdatedUserInfo(firstName, lastName, address, phoneNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserEntity that = (UserEntity) o;
        return emailConfirmed == that.emailConfirmed &&
                userName.equals(that.userName) &&
                email.equals(that.email) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(address, that.address) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                password.equals(that.password) &&
                confirmationCode.equals(that.confirmationCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userName, email, firstName, lastName, address, phoneNumber, password, confirmationCode, emailConfirmed);
    }
}
