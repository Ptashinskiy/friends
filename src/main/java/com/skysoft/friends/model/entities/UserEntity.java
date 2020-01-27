package com.skysoft.friends.model.entities;

import com.skysoft.friends.bussines.common.InvitationInfo;
import com.skysoft.friends.bussines.common.UpdatedUserInfo;
import com.skysoft.friends.bussines.common.UserInfo;
import com.skysoft.friends.bussines.common.UserParametersToUpdate;
import com.skysoft.friends.bussines.exception.FriendsException;
import com.skysoft.friends.bussines.exception.InvitationException;
import com.skysoft.friends.bussines.exception.UserException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

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
    private List<InvitationEntity> inBoxInvitations = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "invitationSender")
    private List<InvitationEntity> outGoingInvitations = new ArrayList<>();

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

    private Integer createRandomConfirmationCode() {
        return new Random().ints(1_000_000, 9_999_999)
                .findFirst().orElse(3_321_526);
    }

    /** Common functionalities: */

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


    /** Functionalities for invitations service: */

    public void sendInvitationTo(UserEntity invitationTargetUser) {
        if (findOutGoingInvitation(invitationTargetUser, this, InvitationStatus.PENDING).isPresent()) {
            throw InvitationException.invitationTransmittedEarly();
        }
        if (findInBoxInvitation(this, invitationTargetUser, InvitationStatus.PENDING).isPresent()) {
            throw InvitationException.reverseInvitation(invitationTargetUser.getUserName());
        }

        InvitationEntity newInvitation = new InvitationEntity(invitationTargetUser, this);
        this.outGoingInvitations.add(newInvitation);
        invitationTargetUser.inBoxInvitations.add(newInvitation);
    }

    public void acceptInvitation(UserEntity invitationSender) {
        InvitationEntity invitation = findInBoxInvitation(this, invitationSender, InvitationStatus.PENDING)
                .orElseThrow(() -> InvitationException.youHaveNoInvitationFromThisUser(invitationSender.getUserName()));
        invitation.updateStatus(InvitationStatus.ACCEPTED);
        invitationSender.friends.add(new FriendEntity(invitationSender, this));
        this.friends.add(new FriendEntity(this, invitationSender));
    }

    public void rejectInvitation(UserEntity invitationSender) {
        InvitationEntity invitation = findInBoxInvitation(this, invitationSender, InvitationStatus.PENDING)
                .orElseThrow(() -> InvitationException.youHaveNoInvitationFromThisUser(invitationSender.getUserName()));
        invitation.updateStatus(InvitationStatus.REJECTED);
    }

    public void cancelInvitation(UserEntity invitationTarget) {
        InvitationEntity invitation = findOutGoingInvitation(invitationTarget, this, InvitationStatus.PENDING)
                .orElseThrow(() -> InvitationException.youHaveNoInvitationToThisUser(invitationTarget.getUserName()));
        invitation.updateStatus(InvitationStatus.CANCELED);
    }

    public List<InvitationInfo> getAllOutGoingInvitations() {
        return outGoingInvitations.stream()
                .map(InvitationInfo::fromEntity)
                .collect(Collectors.toList());
    }

    public List<InvitationInfo> getAllInBoxInvitations() {
        return inBoxInvitations.stream()
                .map(InvitationInfo::fromEntity)
                .collect(Collectors.toList());
    }

    /** Functionalities for friends service: */

    public List<UserInfo> getFriendsInfo() {
        return friends.stream()
                .filter(friendEntity -> friendEntity.getStatus().equals(FriendStatus.FRIENDS))
                .map(FriendEntity::getFriend)
                .map(UserEntity::getUserInfo)
                .collect(Collectors.toList());
    }

    public List<UserInfo> getInvitedUsersInfo() {
        return outGoingInvitations.stream()
                .filter(invitation -> invitation.getStatus().equals(InvitationStatus.PENDING))
                .map(InvitationEntity::getInvitationTarget)
                .map(UserEntity::getUserInfo)
                .collect(Collectors.toList());
    }

    public void deleteFromFriendsAndSendInvitationToDeleter(UserEntity targetUser) {
        this.deleteFriend(targetUser);
        targetUser.deleteFriend(this);
        targetUser.sendInvitationTo(this);
    }

    /** Private methods: */

    private Optional<InvitationEntity> findInBoxInvitation(UserEntity invitationTarget, UserEntity invitationSender, InvitationStatus status) {
        return inBoxInvitations.stream()
                .filter(invitation ->
                        invitation.getInvitationSender().equals(invitationSender) &&
                        invitation.getInvitationTarget().equals(invitationTarget) &&
                        invitation.getStatus().equals(status))
                .findFirst();
    }

    private Optional<InvitationEntity> findOutGoingInvitation(UserEntity invitationTarget, UserEntity invitationSender, InvitationStatus status) {
        return outGoingInvitations.stream()
                .filter(invitation ->
                        invitation.getInvitationSender().equals(invitationSender) &&
                                invitation.getInvitationTarget().equals(invitationTarget) &&
                                invitation.getStatus().equals(status))
                .findFirst();
    }

    private void deleteFriend(UserEntity targetUser) {
        String targetUserName = targetUser.getUserName();
        FriendEntity friend = friends.stream()
                .filter(friendEntity -> friendEntity.getFriend().getUserName().equals(targetUserName)
                        && friendEntity.getStatus().equals(FriendStatus.FRIENDS))
                .findFirst().orElseThrow(() -> FriendsException.youHaveNoSuchFriend(targetUserName));
        friend.deleteFriend();
    }
}
