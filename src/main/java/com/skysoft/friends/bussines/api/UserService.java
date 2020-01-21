package com.skysoft.friends.bussines.api;

import com.skysoft.friends.bussines.common.UpdatedUserInfo;
import com.skysoft.friends.bussines.common.UserInfo;
import com.skysoft.friends.bussines.common.UserParametersToUpdate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    UserInfo getUserInfoByLoginParameter(String loginParameter);

    List<UserInfo> getAllUsersInfo();

    UpdatedUserInfo updateUserInfo(String userEmailBeforeUpdate, UserParametersToUpdate parametersToUpdate);

    void uploadUserIcon(String userLoginParameter, MultipartFile file);
}
