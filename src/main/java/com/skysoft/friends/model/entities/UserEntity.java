package com.skysoft.friends.model.entities;

import com.skysoft.friends.bussines.common.UpdatedUserInfo;
import com.skysoft.friends.bussines.common.UserInfo;
import com.skysoft.friends.bussines.common.UserParametersToUpdate;
import com.skysoft.friends.bussines.exception.UserException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "friendOwner")
    private List<FriendEntity> friends = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "invitationTarget")
    private List<InvitationEntity> invitations = new ArrayList<>();

    public void addInvitation(UserEntity invitationSender) {
        invitations.add(new InvitationEntity(this, invitationSender));
    }

    public void acceptInvitation(InvitationEntity invitation) {
        invitations.remove(invitation);
        UserEntity invitationSender = invitation.getInvitationSender();
        invitationSender.addFriendFromAccepting(this);
        friends.add(new FriendEntity(this, invitationSender));
    }

    private void addFriendFromAccepting(UserEntity acceptedInvitationUser) {
        friends.add(new FriendEntity(this, acceptedInvitationUser));
    }

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

}
