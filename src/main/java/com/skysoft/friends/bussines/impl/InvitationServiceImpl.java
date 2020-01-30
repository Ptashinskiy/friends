package com.skysoft.friends.bussines.impl;

import com.skysoft.friends.bussines.api.InvitationService;
import com.skysoft.friends.bussines.common.AllInBoxInvitations;
import com.skysoft.friends.bussines.common.AllOutGoingInvitations;
import com.skysoft.friends.bussines.exception.FriendsException;
import com.skysoft.friends.bussines.exception.InvitationException;
import com.skysoft.friends.db.repositories.UsersDbService;
import com.skysoft.friends.model.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final UsersDbService usersDbService;

    @Override
    @Transactional
    public void sendInvitation(String invitationSenderUserName, String invitationTargetUserName) {
        UserEntity invitationSender = usersDbService.getUserByUserName(invitationSenderUserName);
        checkForSelfInviting(invitationSender.getUserName(), invitationTargetUserName);
        UserEntity invitationTargetUser = usersDbService.getUserByUserName(invitationTargetUserName);
        checkForAlreadyFriendsWith(invitationSender, invitationTargetUser);
        invitationSender.sendInvitationTo(invitationTargetUser);
    }

    @Override
    @Transactional
    public void acceptInvitation(String currentUserName, String invitationSenderUserName) {
        UserEntity currentUser = usersDbService.getUserByUserName(currentUserName);
        UserEntity invitationSender = usersDbService.getUserByUserName(invitationSenderUserName);
        currentUser.acceptInvitation(invitationSender);
    }

    @Override
    @Transactional
    public void rejectInvitation(String currentUserName, String invitationSenderUserName) {
        UserEntity currentUser = usersDbService.getUserByUserName(currentUserName);
        UserEntity invitationSenderUser = usersDbService.getUserByUserName(invitationSenderUserName);
        currentUser.rejectInvitation(invitationSenderUser);
    }

    @Override
    @Transactional
    public void cancelInvitation(String currentUserName, String invitationTargetUserName) {
        UserEntity currentUser = usersDbService.getUserByUserName(currentUserName);
        UserEntity invitationTargetUser = usersDbService.getUserByUserName(invitationTargetUserName);
        currentUser.cancelInvitation(invitationTargetUser);
    }

    @Override
    @Transactional
    public AllInBoxInvitations getAllInBoxInvitations(String currentUserName) {
        UserEntity currentUser = usersDbService.getUserByUserName(currentUserName);
        return new AllInBoxInvitations(currentUser.getAllInBoxInvitations());
    }

    @Override
    @Transactional
    public AllOutGoingInvitations getAllOutGoingInvitations(String currentUserName) {
        UserEntity currentUser = usersDbService.getUserByUserName(currentUserName);
        return new AllOutGoingInvitations(currentUser.getAllOutGoingInvitations());
    }

    private void checkForSelfInviting(String invitationSenderUserName, String invitationTargetUserName) {
        if (invitationSenderUserName.equals(invitationTargetUserName)) {
            throw InvitationException.selfInviting();
        }
    }

    private void checkForAlreadyFriendsWith(UserEntity friend1, UserEntity friend2) {
        if (usersDbService.alreadyFriends(friend1, friend2)) {
            throw FriendsException.youAlreadyFriendsWith(friend2.getUserName());
        }
    }
}
