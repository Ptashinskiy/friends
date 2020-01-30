package com.skysoft.friends.web.controller;

import com.skysoft.friends.bussines.api.UserService;
import com.skysoft.friends.bussines.common.AllNonFriendUsersInfo;
import com.skysoft.friends.bussines.common.UpdatedUserInfo;
import com.skysoft.friends.bussines.common.UserInfo;
import com.skysoft.friends.bussines.common.UserParametersToUpdate;
import com.skysoft.friends.security.read_token.CurrentUser;
import com.skysoft.friends.web.common.request.UpdateUserInfoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // FIXME: 29.01.2020 Get all users for invite to friends
    @GetMapping
    public ResponseEntity<AllNonFriendUsersInfo> getAllUsersInfo(CurrentUser currentUser) {
        return ResponseEntity.ok(userService.getAllNonFriendsUsersInfo(currentUser.getUserName()));
    }

    @GetMapping("/{userName}")
    public ResponseEntity<UserInfo> getUserInfo(@PathVariable String userName) {
        return ResponseEntity.ok(userService.getUserInfoByUserName(userName));
    }

    @PatchMapping("/update")
    public ResponseEntity<UpdatedUserInfo> updateUserInfo(CurrentUser currentUser, @Valid @RequestBody UpdateUserInfoRequest request) {
        return ResponseEntity.ok(userService.updateUserInfo(currentUser.getUserName(), UserParametersToUpdate.fromRequest(request)));
    }

    @PostMapping(value = "update_icon", consumes = "multipart/form-data")
    public ResponseEntity<Void> updateUserIcon(CurrentUser currentUser, @RequestParam("file") MultipartFile file) {
        userService.uploadUserIcon(currentUser.getUserName(), file);
        return ResponseEntity.ok().build();
    }
}
