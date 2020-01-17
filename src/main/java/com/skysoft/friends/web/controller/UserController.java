package com.skysoft.friends.web.controller;

import com.skysoft.friends.bussines.api.UserService;
import com.skysoft.friends.bussines.common.UpdatedUserInfo;
import com.skysoft.friends.bussines.common.UserInfo;
import com.skysoft.friends.bussines.common.UserParametersToUpdate;
import com.skysoft.friends.security.read_token.CurrentUser;
import com.skysoft.friends.web.common.request.UpdateUserInfoRequest;
import com.skysoft.friends.web.common.response.UpdatedUserInfoResponse;
import com.skysoft.friends.web.common.response.UserInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PatchMapping("/update")
    public ResponseEntity<UpdatedUserInfoResponse> updateUserInfo(CurrentUser currentUser, @Valid @RequestBody UpdateUserInfoRequest request) {
        UpdatedUserInfo updatedUserInfo = userService.updateUserInfo(currentUser.getUserName(), UserParametersToUpdate.fromRequest(request));
        UpdatedUserInfoResponse response = new UpdatedUserInfoResponse(updatedUserInfo.getUserName(), updatedUserInfo.getEmail(),
                updatedUserInfo.getAddress(), updatedUserInfo.getPhoneNumber());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/info")
    public ResponseEntity<UserInfoResponse> getUserInfo(CurrentUser currentUser) {
        UserInfo userInfo = userService.getUserInfoByLoginParameter(currentUser.getUserName());
        UserInfoResponse response = new UserInfoResponse(userInfo.getEmail(), userInfo.getUserName(), userInfo.getAddress(), userInfo.getPhoneNumber());
        return ResponseEntity.ok(response);
    }
}
