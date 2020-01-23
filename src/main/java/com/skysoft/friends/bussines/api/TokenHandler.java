package com.skysoft.friends.bussines.api;

import com.skysoft.friends.bussines.common.UpdatedUserInfo;
import com.skysoft.friends.model.entities.UserEntity;

public interface TokenHandler {

    void checkIsUserHaveNewCredentials(String userLoginParameter, UserEntity updatableUser, UpdatedUserInfo updatedUserInfo);
}
