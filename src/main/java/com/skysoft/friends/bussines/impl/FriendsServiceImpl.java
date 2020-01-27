package com.skysoft.friends.bussines.impl;

import com.skysoft.friends.bussines.api.FriendsService;
import com.skysoft.friends.bussines.common.UserInfo;
import com.skysoft.friends.bussines.exception.NotFoundException;
import com.skysoft.friends.model.entities.UserEntity;
import com.skysoft.friends.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class FriendsServiceImpl implements FriendsService {

    private UserRepository userRepository;

    @Autowired
    public FriendsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public List<UserInfo> getFriendsInfo(String currentUserLoginParameter) {
        UserEntity currentUser = userRepository.findByEmailOrUserName(currentUserLoginParameter)
                .orElseThrow(() -> NotFoundException.userNotFound(currentUserLoginParameter));
        return currentUser.getFriendsInfo();
    }

    @Override
    @Transactional
    public List<UserInfo> getInvitedUsersInfo(String currentUserLoginParameter) {
        UserEntity currentUser = userRepository.findByEmailOrUserName(currentUserLoginParameter)
                .orElseThrow(() -> NotFoundException.userNotFound(currentUserLoginParameter));
        return currentUser.getInvitedUsersInfo();
    }

    @Override
    @Transactional
    public void deleteFromFriends(String currentUserLoginParameter, String targetUserName) {
        UserEntity currentUser = userRepository.findByEmailOrUserName(currentUserLoginParameter)
                .orElseThrow(() -> NotFoundException.userNotFound(currentUserLoginParameter));
        UserEntity targetUser = userRepository.findByEmailOrUserName(targetUserName)
                .orElseThrow(() -> NotFoundException.userNotFound(targetUserName));
        currentUser.deleteFromFriendsAndSendInvitationToDeleter(targetUser);
    }
}
