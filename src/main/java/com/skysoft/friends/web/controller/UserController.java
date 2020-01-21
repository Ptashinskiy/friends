package com.skysoft.friends.web.controller;

import com.skysoft.friends.bussines.api.UserService;
import com.skysoft.friends.bussines.common.UpdatedUserInfo;
import com.skysoft.friends.bussines.common.UserInfo;
import com.skysoft.friends.bussines.common.UserParametersToUpdate;
import com.skysoft.friends.security.read_token.CurrentUser;
import com.skysoft.friends.web.common.request.UpdateUserInfoRequest;
import com.skysoft.friends.web.common.response.AllUsersInfoResponse;
import com.skysoft.friends.web.common.response.UpdatedUserInfoResponse;
import com.skysoft.friends.web.common.response.UserInfoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/info")
    public ResponseEntity<UserInfoResponse> getUserInfo(CurrentUser currentUser) {
        UserInfo userInfo = userService.getUserInfoByLoginParameter(currentUser.getUserName());
        UserInfoResponse response = new UserInfoResponse(userInfo.getEmail(), userInfo.getUserName(), userInfo.getFirstName(),
                userInfo.getLastName(), userInfo.getAddress(), userInfo.getPhoneNumber());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get_all")
    public ResponseEntity<AllUsersInfoResponse> getAllUsersInfo() {
        List<UserInfoResponse> responseList = userService.getAllUsersInfo().stream()
                .map(UserInfoResponse::fromUserInfo).collect(Collectors.toList());
        return ResponseEntity.ok(new AllUsersInfoResponse(responseList));
    }

    @PatchMapping("/update")
    public ResponseEntity<UpdatedUserInfoResponse> updateUserInfo(CurrentUser currentUser, @Valid @RequestBody UpdateUserInfoRequest request) {
        UpdatedUserInfo updatedUserInfo = userService.updateUserInfo(currentUser.getUserName(), UserParametersToUpdate.fromRequest(request));
        UpdatedUserInfoResponse response = new UpdatedUserInfoResponse(updatedUserInfo.getFirstName(), updatedUserInfo.getLastName(),
                updatedUserInfo.getAddress(), updatedUserInfo.getPhoneNumber());
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "update_icon", consumes = "multipart/form-data")
    public ResponseEntity<Void> updateUserIcon(CurrentUser currentUser, @RequestParam("file") MultipartFile file) {
        userService.uploadUserIcon(currentUser.getUserName(), file);
        return ResponseEntity.ok().build();
    }
}
