package com.skysoft.friends.bussines.api;

import com.skysoft.friends.bussines.common.ConfirmationParameters;
import com.skysoft.friends.bussines.common.RegistrationParameters;

public interface AccessService {

    void registerUser(RegistrationParameters registrationParameters);

    void confirmRegistration(ConfirmationParameters confirmationParameters);
}
