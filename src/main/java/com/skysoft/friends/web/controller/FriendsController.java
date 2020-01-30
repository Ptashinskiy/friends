package com.skysoft.friends.web.controller;

import com.skysoft.friends.bussines.api.FriendsService;
import com.skysoft.friends.bussines.common.AllFriendsInfo;
import com.skysoft.friends.bussines.common.AllInvitedUsersInfo;
import com.skysoft.friends.security.read_token.CurrentUser;
import com.skysoft.friends.web.common.request.DeleteFriendRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friends")
public class FriendsController {

    private final FriendsService friendsService;

    @GetMapping
    public ResponseEntity<AllFriendsInfo> getAllFriendsInfo(CurrentUser currentUser) {
        return ResponseEntity.ok(friendsService.getFriendsInfo(currentUser.getUserName()));
    }

    @GetMapping("/invited")
    public ResponseEntity<AllInvitedUsersInfo> getAllInvitedUsersInfo(CurrentUser currentUser) {
        return ResponseEntity.ok(friendsService.getInvitedUsersInfo(currentUser.getUserName()));
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> deleteFromFriends(CurrentUser currentUser, @Valid @RequestBody DeleteFriendRequest request) {
        friendsService.deleteFromFriends(currentUser.getUserName(), request.getTargetUserName());
        return ResponseEntity.ok().build();
    }
}
