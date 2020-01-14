package com.skysoft.friends.bussines.impl;

import com.skysoft.friends.bussines.api.SecurityService;
import com.skysoft.friends.bussines.common.RegistrationParameters;
import com.skysoft.friends.bussines.exception.UserException;
import com.skysoft.friends.model.UserEntity;
import com.skysoft.friends.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SecurityServiceImpl implements SecurityService {

    private UserRepository userRepository;

    @Autowired
    public SecurityServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2AccessToken createToken() {
        Map<String, String> parameters = new HashMap<>();
        return null;
    }

    @Override
    public void registerUser(RegistrationParameters registrationParameters) {
        String email = registrationParameters.getEmail();
        if (!userRepository.existsByEmail(email)) {
            userRepository.save(new UserEntity(registrationParameters.getUserName(), email, registrationParameters.getPassword()));
        } else throw UserException.userAlreadyExist(email);
    }

    private Integer sendConfirmationCodeToEmail(String email) {

    }
}
