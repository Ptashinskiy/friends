package com.skysoft.friends.bussines.impl;

import com.skysoft.friends.bussines.api.FriendsService;
import com.skysoft.friends.bussines.common.AllFriendsInfo;
import com.skysoft.friends.bussines.common.AllInvitedUsersInfo;
import com.skysoft.friends.db.repositories.UsersDbService;
import com.skysoft.friends.model.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class FriendsServiceImpl implements FriendsService {

    private final UsersDbService usersDbService;

    @Override
    @Transactional
    public AllFriendsInfo getFriendsInfo(String currentUserName) {
        UserEntity currentUser = usersDbService.getUserByUserName(currentUserName);
        return new AllFriendsInfo(currentUser.getFriendsInfo());
    }

    @Override
    @Transactional
    public AllInvitedUsersInfo getInvitedUsersInfo(String currentUserName) {
        UserEntity currentUser = usersDbService.getUserByUserName(currentUserName);
        return new AllInvitedUsersInfo(currentUser.getInvitedUsersInfo());
    }

    @Override
    @Transactional
    public void deleteFromFriends(String currentUserName, String targetUserName) {
        UserEntity currentUser = usersDbService.getUserByUserName(currentUserName);
        UserEntity targetUser = usersDbService.getUserByUserName(targetUserName);
        currentUser.deleteFromFriendsAndSendInvitationToDeleter(targetUser);
    }
}
