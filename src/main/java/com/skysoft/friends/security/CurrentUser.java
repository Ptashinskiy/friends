package com.skysoft.friends.security;

import java.util.UUID;

public class CurrentUser {

    private UUID accessId;

    public CurrentUser() {
    }

    public CurrentUser(UUID accessId) {
        this.accessId = accessId;
    }

    public UUID getAccessId() {
        return accessId;
    }

    public void setAccessId(UUID accessId) {
        this.accessId = accessId;
    }
}
