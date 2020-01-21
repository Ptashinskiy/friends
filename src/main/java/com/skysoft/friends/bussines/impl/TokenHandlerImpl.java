/*
package com.skysoft.friends.bussines.impl;

import com.skysoft.friends.bussines.api.MailSender;
import com.skysoft.friends.bussines.api.TokenHandler;
import com.skysoft.friends.bussines.common.UpdatedUserInfo;
import com.skysoft.friends.bussines.exception.TokenException;
import com.skysoft.friends.model.UserEntity;
import com.skysoft.friends.security.build_token.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

@Component
public class TokenHandlerImpl implements TokenHandler {

    private TokenStore tokenStore;
    private MailSender mailSender;

    @Autowired
    public TokenHandlerImpl(TokenStore tokenStore, MailSender mailSender) {
        this.tokenStore = tokenStore;
        this.mailSender = mailSender;
    }

    @Override
    public void checkIsUserHaveNewCredentials(String userLoginParameter, UserEntity updatableUser, UpdatedUserInfo updatedUserInfo) {
        if (updatedUserInfo.isEmailChanged()) {
            removeTokenAndRefreshConfirmation(userLoginParameter, updatableUser);
        } else if (updatedUserInfo.isUserNameChanged()) {
            removeToken(userLoginParameter);
        }
    }

    private void removeTokenAndRefreshConfirmation(String previousLoginParameter, UserEntity user) {
        removeToken(previousLoginParameter);
        user.dropEmailConfirmation();
        String newEmail = user.getEmail();
        Integer newConfirmationCode = user.getCredentials().getConfirmationCode();
        mailSender.sendMessage(newConfirmationCode.toString(), newEmail);
    }

    private void removeToken(String previousLoginParameter) {
        OAuth2AccessToken accessToken = tokenStore.findTokensByClientIdAndUserName(Client.getWebClient().getName(), previousLoginParameter)
                .stream().findFirst().orElseThrow(TokenException::cantFindToken);
        tokenStore.removeRefreshToken(accessToken.getRefreshToken());
        tokenStore.removeAccessToken(accessToken);
    }
}
*/
