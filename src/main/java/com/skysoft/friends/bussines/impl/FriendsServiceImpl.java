package com.skysoft.friends.bussines.impl;

import com.skysoft.friends.bussines.api.FriendsService;
import com.skysoft.friends.bussines.exception.FriendsException;
import com.skysoft.friends.bussines.exception.InvitationException;
import com.skysoft.friends.bussines.exception.NotFoundException;
import com.skysoft.friends.model.entities.InvitationEntity;
import com.skysoft.friends.model.entities.UserEntity;
import com.skysoft.friends.model.repositories.FriendsRepository;
import com.skysoft.friends.model.repositories.InvitationsRepository;
import com.skysoft.friends.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

        if (!invitationsRepository.existsByInvitationTargetAndInvitationSender(invitationTargetUser, invitationSender)) {
            invitationTargetUser.addInvitation(invitationSender);
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
                .findByInvitationSenderAndInvitationTarget(invitationSender, acceptInvitationUser)
                .orElseThrow(() -> InvitationException.youHaveNoInvitationFromThisUser(invitationSenderUserName));

        acceptInvitationUser.acceptInvitation(invitation);
    }

    private void checkForAlreadyFriendsWith(UserEntity friend1, UserEntity friend2) {
        if (friendsRepository.existsByFriendOwnerAndFriend(friend1, friend2)) {
            throw FriendsException.youAlreadyFriendsWith(friend1.getUserName());
        }
    }

    private void checkForSelfInviting(String invitationSenderUserName, String invitationTargetUserName) {
        if (invitationSenderUserName.equals(invitationTargetUserName)) {
            throw InvitationException.selfInviting();
        }
    }

    private void checkForReverseInvite(UserEntity invitationSender, UserEntity invitationTarget) {
        if (invitationsRepository.existsByInvitationTargetAndInvitationSender(invitationSender, invitationTarget)) {
            throw InvitationException.reverseInvitation(invitationTarget.getUserName());
        }
    }
}
