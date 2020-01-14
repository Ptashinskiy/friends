package com.skysoft.friends.bussines.api;

import com.skysoft.friends.bussines.common.RegistrationParameters;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

public interface SecurityService {

    OAuth2AccessToken createToken();

    void registerUser(RegistrationParameters registrationParameters);
}
