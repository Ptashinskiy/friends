package com.skysoft.friends.bussines.impl;

import com.skysoft.friends.bussines.api.AccessService;
import com.skysoft.friends.bussines.api.MailSender;
import com.skysoft.friends.bussines.common.RegistrationParameters;
import com.skysoft.friends.bussines.exception.RegistrationException;
import com.skysoft.friends.db.repositories.UsersDbService;
import com.skysoft.friends.model.entities.RegistrationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AccessServiceImpl implements AccessService {

    private static final String WELCOME_MESSAGE = "To confirm your account, please click here : ";
    private static final String HOST = "http://localhost:8080";
    private static final String CONFIRMATION_ENDPOINT = "/access/confirm";
    private static final String REQUEST_PARAM = "token";
    private static final String MESSAGE_SUBJECT = "Registry confirmation.";

    private final UsersDbService usersDbService;
    private final MailSender mailSender;

    // FIXME: 29.01.2020 refactor to db service

    @Override
    @Transactional
    public void registerUser(RegistrationParameters registrationParameters) {
        if (usersDbService.suchUserNotExist(registrationParameters.getEmail(), registrationParameters.getUserName())) {
            RegistrationEntity registrationEntity = registrationParameters.toEntity();
            usersDbService.saveRegistration(registrationEntity);
//            sendConfirmationToEmail(registrationEntity);
        } else {
            throw RegistrationException.alreadyRegistered();
        }
    }

    // FIXME: 29.01.2020 move token to registration entity
    // FIXME: 29.01.2020 isUserAlreadyRegistered(username,email)

    @Override
    @Transactional
    public void confirmRegistration(String token) {
        RegistrationEntity registrationEntity = usersDbService.getRegisteredUserByVerificationToken(token);
        if (usersDbService.suchUserNotConfirmed(registrationEntity.getEmail(), registrationEntity.getUserName())) {
            registrationEntity.confirmRegistration();
        } else throw RegistrationException.alreadyConfirmed();
        usersDbService.saveNewUser(registrationEntity.toUserEntity());
    }

    private void sendConfirmationToEmail(RegistrationEntity registrationEntity) {
        String message =  WELCOME_MESSAGE + HOST + CONFIRMATION_ENDPOINT + "?" + REQUEST_PARAM + "=" + registrationEntity.getVerificationToken();
        mailSender.sendMessage(message, registrationEntity.getEmail(), MESSAGE_SUBJECT);
    }
}
