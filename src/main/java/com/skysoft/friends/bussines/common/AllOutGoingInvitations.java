package com.skysoft.friends.bussines.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllOutGoingInvitations {
    private List<InvitationInfo> allOutGoingInvitationsInfo;
}
