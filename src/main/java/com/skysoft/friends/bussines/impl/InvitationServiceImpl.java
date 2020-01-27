package com.skysoft.friends.bussines.impl;

import com.skysoft.friends.bussines.api.InvitationService;
import com.skysoft.friends.bussines.common.InvitationInfo;
import com.skysoft.friends.bussines.exception.FriendsException;
import com.skysoft.friends.bussines.exception.InvitationException;
import com.skysoft.friends.bussines.exception.NotFoundException;
import com.skysoft.friends.model.entities.FriendStatus;
import com.skysoft.friends.model.entities.UserEntity;
import com.skysoft.friends.model.repositories.FriendsRepository;
import com.skysoft.friends.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class InvitationServiceImpl implements InvitationService {

    private UserRepository userRepository;
    private FriendsRepository friendsRepository;

    @Autowired
    public InvitationServiceImpl(UserRepository userRepository, FriendsRepository friendsRepository) {
        this.userRepository = userRepository;
        this.friendsRepository = friendsRepository;
    }

    @Override
    @Transactional
    public void sendInvitation(String invitationSenderLoginParameter, String invitationTargetUserName) {
        UserEntity invitationSender = userRepository.findByEmailOrUserName(invitationSenderLoginParameter)
                .orElseThrow(() -> NotFoundException.userNotFound(invitationSenderLoginParameter));

        checkForSelfInviting(invitationSender.getUserName(), invitationTargetUserName);

        UserEntity invitationTargetUser = userRepository.findByEmailOrUserName(invitationTargetUserName)
                .orElseThrow(() -> NotFoundException.userNotFound(invitationTargetUserName));

        checkForAlreadyFriendsWith(invitationTargetUser, invitationSender);

        invitationSender.sendInvitationTo(invitationTargetUser);
    }

    @Override
    @Transactional
    public void acceptInvitation(String acceptInvitationUserLoginParameter, String invitationSenderUserName) {
        UserEntity currentUser = userRepository.findByEmailOrUserName(acceptInvitationUserLoginParameter)
                .orElseThrow(() -> NotFoundException.userNotFound(acceptInvitationUserLoginParameter));
        UserEntity invitationSender = userRepository.findByEmailOrUserName(invitationSenderUserName)
                .orElseThrow(() -> NotFoundException.userNotFound(invitationSenderUserName));
        currentUser.acceptInvitation(invitationSender);
    }

    @Override
    @Transactional
    public void rejectInvitation(String currentUserLoginParameter, String invitationSenderUserName) {
        UserEntity currentUser = userRepository.findByEmailOrUserName(currentUserLoginParameter)
                .orElseThrow(() -> NotFoundException.userNotFound(currentUserLoginParameter));
        UserEntity invitationSenderUser = userRepository.findByEmailOrUserName(invitationSenderUserName)
                .orElseThrow(() -> NotFoundException.userNotFound(invitationSenderUserName));
        currentUser.rejectInvitation(invitationSenderUser);
    }

    @Override
    @Transactional
    public void cancelInvitation(String currentUserLoginParameter, String invitationTargetUserName) {
        UserEntity currentUser = userRepository.findByEmailOrUserName(currentUserLoginParameter)
                .orElseThrow(() -> NotFoundException.userNotFound(currentUserLoginParameter));
        UserEntity invitationTargetUser = userRepository.findByEmailOrUserName(invitationTargetUserName)
                .orElseThrow(() -> NotFoundException.userNotFound(invitationTargetUserName));
        currentUser.cancelInvitation(invitationTargetUser);
    }

    @Override
    @Transactional
    public List<InvitationInfo> getAllOutGoingInvitations(String currentUserLoginParameter) {
        UserEntity currentUser = userRepository.findByEmailOrUserName(currentUserLoginParameter)
                .orElseThrow(() -> NotFoundException.userNotFound(currentUserLoginParameter));
        return currentUser.getAllOutGoingInvitations();
    }

    @Override
    @Transactional
    public List<InvitationInfo> getAllInBoxInvitations(String userLoginParameter) {
        UserEntity currentUser = userRepository.findByEmailOrUserName(userLoginParameter)
                .orElseThrow(() -> NotFoundException.userNotFound(userLoginParameter));
        return currentUser.getAllInBoxInvitations();
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
}
