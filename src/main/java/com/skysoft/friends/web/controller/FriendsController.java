package com.skysoft.friends.web.controller;

import com.skysoft.friends.bussines.api.FriendsService;
import com.skysoft.friends.security.read_token.CurrentUser;
import com.skysoft.friends.web.common.request.DeleteFriendRequest;
import com.skysoft.friends.web.common.response.AllFriendsInfoResponse;
import com.skysoft.friends.web.common.response.AllInvitedFriendsInfoResponse;
import com.skysoft.friends.web.common.response.FriendInfoResponse;
import com.skysoft.friends.web.common.response.InvitedFriendInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/friends")
public class FriendsController {

    private FriendsService friendsService;

    @Autowired
    public FriendsController(FriendsService friendsService) {
        this.friendsService = friendsService;
    }

    @GetMapping
    public ResponseEntity<AllFriendsInfoResponse> getAllFriendsInfo(CurrentUser currentUser) {
        List<FriendInfoResponse> allFriendsInfoResponse = friendsService.getAllFriendsInfo(currentUser.getUserName()).stream()
                .map(FriendInfoResponse::fromUserInfo)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new AllFriendsInfoResponse(allFriendsInfoResponse));
    }

    @GetMapping("/invited")
    public ResponseEntity<AllInvitedFriendsInfoResponse> getAllInvitedFriendsInfo(CurrentUser currentUser) {
        List<InvitedFriendInfoResponse> invitedFriendsInfo = friendsService.getAllInvitedFriendsInfo(currentUser.getUserName()).stream()
                .map(InvitedFriendInfoResponse::fromUserInfo)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new AllInvitedFriendsInfoResponse(invitedFriendsInfo));
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> deleteFromFriends(CurrentUser currentUser, @RequestBody DeleteFriendRequest request) {
        friendsService.deleteFromFriends(currentUser.getUserName(), request.getTargetUserName());
        return ResponseEntity.ok().build();
    }
}
