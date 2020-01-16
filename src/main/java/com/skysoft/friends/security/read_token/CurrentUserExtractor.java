package com.skysoft.friends.security.read_token;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CurrentUserExtractor {

    public CurrentUser extractUserFromToken() {
        Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
        OAuth2AuthenticationDetails oAuth2AuthenticationDetails = (OAuth2AuthenticationDetails) details;
        Object decodedDetails = oAuth2AuthenticationDetails.getDecodedDetails();
        Map map = (Map) decodedDetails;
        String email = (String) map.get("user_name");
        return new CurrentUser(email);
    }
}
