package com.skysoft.friends.bussines.impl;

import com.skysoft.friends.bussines.api.TokenService;
import com.skysoft.friends.bussines.api.UserService;
import com.skysoft.friends.bussines.common.UpdatedUserInfo;
import com.skysoft.friends.bussines.common.UserInfo;
import com.skysoft.friends.bussines.common.UserParametersToUpdate;
import com.skysoft.friends.bussines.exception.NotFoundException;
import com.skysoft.friends.model.UserEntity;
import com.skysoft.friends.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private TokenService tokenService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @Override
    @Transactional
    public UserInfo getUserInfoByLoginParameter(String loginParameter) {
        UserEntity user = userRepository.findByEmailOrUserName(loginParameter).orElseThrow(() -> NotFoundException.userNotFound(loginParameter));
        return user.getUserInfo();
    }

    @Override
    @Transactional
    public UpdatedUserInfo updateUserInfo(String userLoginParameter, UserParametersToUpdate parametersToUpdate) {
        UserEntity updatableUser = userRepository.findByEmailOrUserName(userLoginParameter)
                .orElseThrow(() -> NotFoundException.userNotFound(userLoginParameter));
        UpdatedUserInfo updatedUserInfo = updatableUser.updateInfo(parametersToUpdate);
        tokenService.checkIsUserHaveNewCredentials(userLoginParameter, updatableUser, updatedUserInfo);
        return updatedUserInfo;
    }
}
