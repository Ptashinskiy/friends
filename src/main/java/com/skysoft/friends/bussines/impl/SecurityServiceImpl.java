package com.skysoft.friends.bussines.impl;

import com.skysoft.friends.bussines.api.SecurityService;
import com.skysoft.friends.bussines.common.ConfirmationParameters;
import com.skysoft.friends.bussines.common.RegistrationParameters;
import com.skysoft.friends.bussines.exception.NotFoundException;
import com.skysoft.friends.bussines.exception.UserException;
import com.skysoft.friends.model.UserEntity;
import com.skysoft.friends.model.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class SecurityServiceImpl implements SecurityService {

    private UserRepository userRepository;
    private JavaMailSender javaMailSender;

    @Autowired
    public SecurityServiceImpl(UserRepository userRepository, JavaMailSender javaMailSender) {
        this.userRepository = userRepository;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public OAuth2AccessToken createToken() {
        Map<String, String> parameters = new HashMap<>();
        return null;
    }

    @Override
    @Transactional
    public void registerUser(RegistrationParameters registrationParameters) {
        String email = registrationParameters.getEmail();
        if (!userRepository.existsByEmail(email)) {
            userRepository.save(new UserEntity(registrationParameters.getUserName(), email, registrationParameters.getPassword()));
            sendConfirmationCodeToEmail(email);
        } else throw UserException.userAlreadyExist(email);
    }

    @Override
    @Transactional
    public void confirmRegistration(ConfirmationParameters confirmationParameters) {
        String email = confirmationParameters.getEmail();
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> NotFoundException.userNotFound(email));
        if (userEntity.isNotConfirmed()) {
            userEntity.confirmRegistration(confirmationParameters.getConfirmationCode());
        } else throw UserException.confirmationNotRequired(email);
    }

    private void sendConfirmationCodeToEmail(String email) {
        Integer confirmationCode = userRepository.getConfirmationCodeByEmail(email)
                .orElseThrow(() -> UserException.confirmationNotRequired(email));
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Confirmation code");
        message.setText(confirmationCode.toString());
        javaMailSender.send(message);
    }
}
