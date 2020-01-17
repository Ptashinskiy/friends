package com.skysoft.friends.bussines.api;

import com.skysoft.friends.bussines.common.UpdatedUserInfo;
import com.skysoft.friends.bussines.common.UserInfo;
import com.skysoft.friends.bussines.common.UserParametersToUpdate;

public interface UserService {

    UserInfo getUserInfoByLoginParameter(String loginParameter);

    UpdatedUserInfo updateUserInfo(String userEmailBeforeUpdate, UserParametersToUpdate parametersToUpdate);
}
