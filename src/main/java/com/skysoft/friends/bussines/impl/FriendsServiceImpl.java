package com.skysoft.friends.bussines.impl;

import com.skysoft.friends.bussines.api.FriendsService;
import com.skysoft.friends.bussines.common.InvitationInfo;
import com.skysoft.friends.bussines.common.UserInfo;
import com.skysoft.friends.bussines.exception.FriendsException;
import com.skysoft.friends.bussines.exception.InvitationException;
import com.skysoft.friends.bussines.exception.NotFoundException;
import com.skysoft.friends.model.entities.*;
import com.skysoft.friends.model.repositories.FriendsRepository;
import com.skysoft.friends.model.repositories.InvitationsRepository;
import com.skysoft.friends.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendsServiceImpl implements FriendsService {

    private UserRepository userRepository;
    private FriendsRepository friendsRepository;
    private InvitationsRepository invitationsRepository;

    @Autowired
    public FriendsServiceImpl(UserRepository userRepository, InvitationsRepository invitationsRepository, FriendsRepository friendsRepository) {
        this.userRepository = userRepository;
        this.invitationsRepository = invitationsRepository;
        this.friendsRepository = friendsRepository;
    }

    @Override
    @Transactional
    public void sendInvitationToBeFriends(String invitationSenderLoginParameter, String invitationTargetUserName) {
        UserEntity invitationSender = userRepository.findByEmailOrUserName(invitationSenderLoginParameter)
                .orElseThrow(() -> NotFoundException.userNotFound(invitationSenderLoginParameter));

        checkForSelfInviting(invitationSender.getUserName(), invitationTargetUserName);

        UserEntity invitationTargetUser = userRepository.findByEmailOrUserName(invitationTargetUserName)
        .orElseThrow(() -> NotFoundException.userNotFound(invitationTargetUserName));

        checkForAlreadyFriendsWith(invitationTargetUser, invitationSender);
        checkForReverseInvite(invitationSender, invitationTargetUser);

        if (!invitationsRepository.existsByInvitationTargetAndInvitationSenderAndStatus
                (invitationTargetUser, invitationSender, InvitationStatus.PENDING)) {
            invitationTargetUser.addInBoxInvitationToTarget(invitationSender);
        } else {
            throw InvitationException.invitationTransmittedEarly();
        }
    }

    @Override
    @Transactional
    public void acceptInvitation(String acceptInvitationUserLoginParameter, String invitationSenderUserName) {
        UserEntity acceptInvitationUser = userRepository.findByEmailOrUserName(acceptInvitationUserLoginParameter)
                .orElseThrow(() -> NotFoundException.userNotFound(acceptInvitationUserLoginParameter));
        UserEntity invitationSender = userRepository.findByEmailOrUserName(invitationSenderUserName)
                .orElseThrow(() -> NotFoundException.userNotFound(invitationSenderUserName));

        checkForAlreadyFriendsWith(invitationSender, acceptInvitationUser);

        InvitationEntity invitation = invitationsRepository
                .findByInvitationSenderAndInvitationTargetAndStatus(invitationSender, acceptInvitationUser, InvitationStatus.PENDING)
                .orElseThrow(() -> InvitationException.youHaveNoInvitationFromThisUser(invitationSenderUserName));

        acceptInvitationUser.acceptInvitation(invitation);
    }

    @Override
    @Transactional
    public void rejectInvitation(String currentUserLoginParameter, String invitationSenderUserName) {
        UserEntity rejectInvitationUser = userRepository.findByEmailOrUserName(currentUserLoginParameter)
                .orElseThrow(() -> NotFoundException.userNotFound(currentUserLoginParameter));
        UserEntity invitationSenderUser = userRepository.findByEmailOrUserName(invitationSenderUserName)
                .orElseThrow(() -> NotFoundException.userNotFound(invitationSenderUserName));

        InvitationEntity invitation = invitationsRepository.findByInvitationSenderAndInvitationTargetAndStatus(
                invitationSenderUser, rejectInvitationUser, InvitationStatus.PENDING
        ).orElseThrow(() -> InvitationException.youHaveNoInvitationFromThisUser(invitationSenderUserName));

        rejectInvitationUser.rejectInvitation(invitation);
    }

    @Override
    @Transactional
    public void cancelInvitation(String currentUserLoginParameter, String invitationTargetUserName) {
        UserEntity cancelInvitationUser = userRepository.findByEmailOrUserName(currentUserLoginParameter)
                .orElseThrow(() -> NotFoundException.userNotFound(currentUserLoginParameter));
        UserEntity invitationTargetUser = userRepository.findByEmailOrUserName(invitationTargetUserName)
                .orElseThrow(() -> NotFoundException.userNotFound(invitationTargetUserName));

        InvitationEntity invitation = invitationsRepository.findByInvitationSenderAndInvitationTargetAndStatus(
                cancelInvitationUser, invitationTargetUser, InvitationStatus.PENDING)
                .orElseThrow(() -> InvitationException.youHaveNoInvitationToThisUser(invitationTargetUserName));
        cancelInvitationUser.cancelInvitation(invitation);
    }

    @Override
    public List<InvitationInfo> getAllOutGoingInvitationsByUserLoginParameter(String currentUserLoginParameter) {
        UserEntity currentUser = userRepository.findByEmailOrUserName(currentUserLoginParameter)
                .orElseThrow(() -> NotFoundException.userNotFound(currentUserLoginParameter));

        return currentUser.getAllOutGoingInvitations();
    }

    @Override
    public List<UserInfo> getAllFriendsInfo(String currentUserLoginParameter) {
        UserEntity currentUser = userRepository.findByEmailOrUserName(currentUserLoginParameter)
                .orElseThrow(() -> NotFoundException.userNotFound(currentUserLoginParameter));

        return currentUser.getFriends().stream()
                .filter(friendEntity -> friendEntity.getStatus().equals(FriendStatus.FRIENDS))
                .map(FriendEntity::getFriend)
                .map(UserEntity::getUserInfo)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserInfo> getAllInvitedFriendsInfo(String currentUserLoginParameter) {
        UserEntity currentUser = userRepository.findByEmailOrUserName(currentUserLoginParameter)
                .orElseThrow(() -> NotFoundException.userNotFound(currentUserLoginParameter));

        return currentUser.getOutGoingInvitations().stream()
                .filter(invitation -> invitation.getStatus().equals(InvitationStatus.PENDING))
                .map(InvitationEntity::getInvitationTarget)
                .map(UserEntity::getUserInfo)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteFromFriends(String currentUserLoginParameter, String targetUserName) {
        UserEntity currentUser = userRepository.findByEmailOrUserName(currentUserLoginParameter)
                .orElseThrow(() -> NotFoundException.userNotFound(currentUserLoginParameter));

        UserEntity targetUser = userRepository.findByEmailOrUserName(targetUserName)
                .orElseThrow(() -> NotFoundException.userNotFound(targetUserName));

        currentUser.deleteFromFriends(targetUser);
    }

    @Override
    @Transactional
    public List<InvitationInfo> getAllInBoxInvitationsByUserLoginParameter(String userLoginParameter) {
        UserEntity currentUser = userRepository.findByEmailOrUserName(userLoginParameter)
                .orElseThrow(() -> NotFoundException.userNotFound(userLoginParameter));

        return currentUser.getInBoxInvitations().stream()
                .map(InvitationInfo::fromEntity)
                .collect(Collectors.toList());
    }

    private void checkForAlreadyFriendsWith(UserEntity friend1, UserEntity friend2) {
        if (friendsRepository.existsByFriendOwnerAndFriendAndStatus(friend1, friend2, FriendStatus.FRIENDS)) {
            throw FriendsException.youAlreadyFriendsWith(friend1.getUserName());
        }
    }

    private void checkForSelfInviting(String invitationSenderUserName, String invitationTargetUserName) {
        if (invitationSenderUserName.equals(invitationTargetUserName)) {
            throw InvitationException.selfInviting();
        }
    }

    private void checkForReverseInvite(UserEntity invitationSender, UserEntity invitationTarget) {
        if (invitationsRepository.existsByInvitationTargetAndInvitationSenderAndStatus(invitationSender, invitationTarget, InvitationStatus.PENDING)) {
            throw InvitationException.reverseInvitation(invitationTarget.getUserName());
        }
    }
}
