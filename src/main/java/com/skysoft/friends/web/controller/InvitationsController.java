package com.skysoft.friends.web.controller;

import com.skysoft.friends.bussines.api.InvitationService;
import com.skysoft.friends.security.read_token.CurrentUser;
import com.skysoft.friends.web.common.request.AcceptInvitationRequest;
import com.skysoft.friends.web.common.request.CancelInvitationRequest;
import com.skysoft.friends.web.common.request.RejectInvitationRequest;
import com.skysoft.friends.web.common.request.SendInvitationRequest;
import com.skysoft.friends.web.common.response.AllInvitationsResponse;
import com.skysoft.friends.web.common.response.InvitationInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/invitations")
public class InvitationsController {

    private InvitationService invitationService;

    @Autowired
    public InvitationsController(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

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
    public ResponseEntity<AllInvitationsResponse> getAllInBoxInvitations(CurrentUser currentUser) {
        List<InvitationInfoResponse> invitationsInfoResponse = invitationService.getAllInBoxInvitations(currentUser.getUserName())
                .stream()
                .map(InvitationInfoResponse::fromInvitationInfo)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new AllInvitationsResponse(invitationsInfoResponse));
    }

    @GetMapping("/outgoing")
    public ResponseEntity<AllInvitationsResponse> getAllOutGoingInvitations(CurrentUser currentUser) {
        List<InvitationInfoResponse> invitationsInfoResponse = invitationService.getAllOutGoingInvitations(currentUser.getUserName())
                .stream()
                .map(InvitationInfoResponse::fromInvitationInfo)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new AllInvitationsResponse(invitationsInfoResponse));
    }
}
