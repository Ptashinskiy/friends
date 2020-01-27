package com.skysoft.friends.web.common.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendInvitationRequest {

    @NotBlank
    private String invitationTargetUserName;
}
