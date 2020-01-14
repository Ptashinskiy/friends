package com.skysoft.friends.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class CurrentUserExtractor {

    private static final String ACCESS_ID = "user_name";

    public CurrentUser extractUserFromToken() {
        Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
        OAuth2AuthenticationDetails oAuth2AuthenticationDetails = (OAuth2AuthenticationDetails) details;
        Object decodedDetails = oAuth2AuthenticationDetails.getDecodedDetails();
        Map map = (Map) decodedDetails;
        UUID accessId = UUID.fromString((String) map.get(ACCESS_ID));
        return new CurrentUser(accessId);
    }
}
