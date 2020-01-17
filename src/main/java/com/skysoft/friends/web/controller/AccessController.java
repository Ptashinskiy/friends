package com.skysoft.friends.web.controller;

import com.skysoft.friends.bussines.api.AccessService;
import com.skysoft.friends.bussines.common.ConfirmationParameters;
import com.skysoft.friends.bussines.common.RegistrationParameters;
import com.skysoft.friends.web.common.request.ConfirmRegistrationRequest;
import com.skysoft.friends.web.common.request.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/access")
public class AccessController {

    private AccessService accessService;

    @Autowired
    public AccessController(AccessService accessService) {
        this.accessService = accessService;
    }

    @PostMapping("/registration")
    public ResponseEntity<Void> registration(@Valid @RequestBody RegistrationRequest request) {
        accessService.registerUser(RegistrationParameters.fromRequest(request));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/confirm")
    public ResponseEntity<Void> confirmRegistration(@Valid @RequestBody ConfirmRegistrationRequest request) {
        accessService.confirmRegistration(ConfirmationParameters.fromRequest(request));
        return ResponseEntity.ok().build();
    }
}
