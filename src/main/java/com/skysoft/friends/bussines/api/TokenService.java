package com.skysoft.friends.bussines.api;

import com.skysoft.friends.bussines.common.UpdatedUserInfo;
import com.skysoft.friends.model.UserEntity;

public interface TokenService {

    void checkIsUserHaveNewCredentials(String userLoginParameter, UserEntity updatableUser, UpdatedUserInfo updatedUserInfo);
}
