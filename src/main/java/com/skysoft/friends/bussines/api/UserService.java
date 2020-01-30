package com.skysoft.friends.bussines.api;

import com.skysoft.friends.bussines.common.AllNonFriendUsersInfo;
import com.skysoft.friends.bussines.common.UpdatedUserInfo;
import com.skysoft.friends.bussines.common.UserInfo;
import com.skysoft.friends.bussines.common.UserParametersToUpdate;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    UserInfo getUserInfoByUserName(String loginParameter);

    AllNonFriendUsersInfo getAllNonFriendsUsersInfo(String currentUserName);

    UpdatedUserInfo updateUserInfo(String currentUserName, UserParametersToUpdate parametersToUpdate);

    void uploadUserIcon(String currentUserName, MultipartFile file);
}
