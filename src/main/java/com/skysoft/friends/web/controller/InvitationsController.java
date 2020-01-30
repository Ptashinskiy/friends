package com.skysoft.friends.web.controller;

import com.skysoft.friends.bussines.api.InvitationService;
import com.skysoft.friends.bussines.common.AllInBoxInvitations;
import com.skysoft.friends.bussines.common.AllOutGoingInvitations;
import com.skysoft.friends.security.read_token.CurrentUser;
import com.skysoft.friends.web.common.request.AcceptInvitationRequest;
import com.skysoft.friends.web.common.request.CancelInvitationRequest;
import com.skysoft.friends.web.common.request.RejectInvitationRequest;
import com.skysoft.friends.web.common.request.SendInvitationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/invitations")
public class InvitationsController {

    private final InvitationService invitationService;

    @PostMapping("/send")
    public ResponseEntity<Void> sendInvitation(CurrentUser currentUser, @Valid @RequestBody SendInvitationRequest request) {
        invitationService.sendInvitation(currentUser.getUserName(), request.getInvitationTargetUserName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/accept")
    public ResponseEntity<Void> acceptInvitation(CurrentUser currentUser, @Valid @RequestBody AcceptInvitationRequest request) {
        invitationService.acceptInvitation(currentUser.getUserName(), request.getInvitationSenderUserName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reject")
    public ResponseEntity<Void> rejectInvitation(CurrentUser currentUser, @Valid @RequestBody RejectInvitationRequest request) {
        invitationService.rejectInvitation(currentUser.getUserName(), request.getInvitationSenderUserName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cancel")
    public ResponseEntity<Void> cancelInvitation(CurrentUser currentUser, @Valid @RequestBody CancelInvitationRequest request) {
        invitationService.cancelInvitation(currentUser.getUserName(), request.getInvitationTargetUserName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/inbox")
    public ResponseEntity<AllInBoxInvitations> getAllInBoxInvitations(CurrentUser currentUser) {
        return ResponseEntity.ok(invitationService.getAllInBoxInvitations(currentUser.getUserName()));
    }

    @GetMapping("/outgoing")
    public ResponseEntity<AllOutGoingInvitations> getAllOutGoingInvitations(CurrentUser currentUser) {
        return ResponseEntity.ok(invitationService.getAllOutGoingInvitations(currentUser.getUserName()));
    }
}
