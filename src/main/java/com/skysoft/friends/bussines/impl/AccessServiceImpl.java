package com.skysoft.friends.bussines.impl;

import com.skysoft.friends.bussines.api.AccessService;
import com.skysoft.friends.bussines.api.MailSender;
import com.skysoft.friends.bussines.common.ConfirmationParameters;
import com.skysoft.friends.bussines.common.RegistrationParameters;
import com.skysoft.friends.bussines.exception.NotFoundException;
import com.skysoft.friends.bussines.exception.UserException;
import com.skysoft.friends.model.entities.UserEntity;
import com.skysoft.friends.model.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
public class AccessServiceImpl implements AccessService {

    private static String EMAIL_SUBJECT_CONFIRMATION = "Confirmation code";

    private UserRepository userRepository;
    private MailSender mailSender;

    @Autowired
    public AccessServiceImpl(UserRepository userRepository, MailSender mailSender) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }

    @Override
    @Transactional
    public void registerUser(RegistrationParameters registrationParameters) {
        String email = registrationParameters.getEmail();
        if (!userRepository.existsByEmail(email)) {
            UserEntity newUser = new UserEntity(registrationParameters.getUserName(), email, registrationParameters.getFirstName(),
                    registrationParameters.getLastName(), registrationParameters.getAddress(), registrationParameters.getPhoneNumber(),
                    registrationParameters.getPassword());
            userRepository.save(newUser);
            sendConfirmationCodeToEmail(email);
        } else throw UserException.userAlreadyExist(email);
    }

    @Override
    @Transactional
    public void confirmRegistration(ConfirmationParameters confirmationParameters) {
        String email = confirmationParameters.getEmail();
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> NotFoundException.userNotFound(email));
        if (userEntity.isEmailNotConfirmed()) {
            userEntity.confirmRegistration(confirmationParameters.getConfirmationCode());
        } else throw UserException.confirmationNotRequired(email);
    }

    private void sendConfirmationCodeToEmail(String email) {
        Integer confirmationCode = userRepository.getConfirmationCodeByEmail(email)
                .orElseThrow(() -> NotFoundException.userNotFound(email));
        mailSender.sendMessage(confirmationCode.toString(), email, EMAIL_SUBJECT_CONFIRMATION);
    }
}
