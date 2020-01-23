package com.skysoft.friends.web.controller;

import com.skysoft.friends.bussines.api.FriendsService;
import com.skysoft.friends.security.read_token.CurrentUser;
import com.skysoft.friends.web.common.request.AcceptInvitationRequest;
import com.skysoft.friends.web.common.request.CancelInvitationRequest;
import com.skysoft.friends.web.common.request.RejectInvitationRequest;
import com.skysoft.friends.web.common.request.SendInvitationRequest;
import com.skysoft.friends.web.common.response.InvitationInfoResponse;
import com.skysoft.friends.web.common.response.AllInvitationsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/invitations")
public class FriendsController {

    private FriendsService friendsService;

    @Autowired
    public FriendsController(FriendsService friendsService) {
        this.friendsService = friendsService;
    }

    @PostMapping("/send")
    public ResponseEntity<Void> sendInvitation(CurrentUser currentUser, @RequestBody SendInvitationRequest request) {
        friendsService.sendInvitationToBeFriends(currentUser.getUserName(), request.getInvitationTargetUserName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/accept")
    public ResponseEntity<Void> acceptInvitation(CurrentUser currentUser, @RequestBody AcceptInvitationRequest request) {
        friendsService.acceptInvitation(currentUser.getUserName(), request.getInvitationSenderUserName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reject")
    public ResponseEntity<Void> rejectInvitation(CurrentUser currentUser, @RequestBody RejectInvitationRequest request) {
        friendsService.rejectInvitation(currentUser.getUserName(), request.getInvitationSenderUserName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cancel")
    public ResponseEntity<Void> cancelInvitation(CurrentUser currentUser, @RequestBody CancelInvitationRequest request) {
        friendsService.cancelInvitation(currentUser.getUserName(), request.getInvitationTargetUserName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/inbox")
    public ResponseEntity<AllInvitationsResponse> getAllInBoxInvitations(CurrentUser currentUser) {
        List<InvitationInfoResponse> invitationsInfoResponse = friendsService.getAllInBoxInvitationsByUserLoginParameter(currentUser.getUserName())
                .stream()
                .map(InvitationInfoResponse::fromInvitationInfo)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new AllInvitationsResponse(invitationsInfoResponse));
    }

    @GetMapping("/outgoing")
    public ResponseEntity<AllInvitationsResponse> getAllOutGoingInvitations(CurrentUser currentUser) {
        List<InvitationInfoResponse> invitationsInfoResponse = friendsService.getAllOutGoingInvitationsByUserLoginParameter(currentUser.getUserName())
                .stream()
                .map(InvitationInfoResponse::fromInvitationInfo)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new AllInvitationsResponse(invitationsInfoResponse));
    }
}
