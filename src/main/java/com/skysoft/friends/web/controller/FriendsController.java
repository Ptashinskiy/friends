package com.skysoft.friends.web.controller;

import com.skysoft.friends.bussines.api.FriendsService;
import com.skysoft.friends.security.read_token.CurrentUser;
import com.skysoft.friends.web.common.request.AcceptInvitationRequest;
import com.skysoft.friends.web.common.request.SendInvitationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friends")
public class FriendsController {

    private FriendsService friendsService;

    @Autowired
    public FriendsController(FriendsService friendsService) {
        this.friendsService = friendsService;
    }

    @PostMapping("/invitations/send")
    public ResponseEntity<Void> sendInvitation(CurrentUser currentUser, @RequestBody SendInvitationRequest request) {
        friendsService.sendInvitationToBeFriends(currentUser.getUserName(), request.getInvitationTargetUserName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/invitations/accept")
    public ResponseEntity<Void> acceptInvitation(CurrentUser currentUser, @RequestBody AcceptInvitationRequest request) {
        friendsService.acceptInvitation(currentUser.getUserName(), request.getInvitationSenderUserName());
        return ResponseEntity.ok().build();
    }
}
