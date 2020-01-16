package com.skysoft.friends.web.controller;

import com.skysoft.friends.bussines.api.SecurityService;
import com.skysoft.friends.bussines.common.ConfirmationParameters;
import com.skysoft.friends.bussines.common.RegistrationParameters;
import com.skysoft.friends.bussines.exception.NotFoundException;
import com.skysoft.friends.model.UserEntity;
import com.skysoft.friends.model.repositories.UserRepository;
import com.skysoft.friends.security.read_token.CurrentUser;
import com.skysoft.friends.web.common.request.ConfirmRegistrationRequest;
import com.skysoft.friends.web.common.request.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/access")
public class SecurityController {

    private SecurityService securityService;
    private UserRepository userRepository;

    @Autowired
    public SecurityController(SecurityService securityService, UserRepository userRepository) {
        this.securityService = securityService;
        this.userRepository = userRepository;
    }

    @PostMapping("/registration")
    public ResponseEntity<Void> registration(@Valid @RequestBody RegistrationRequest request) {
        securityService.registerUser(RegistrationParameters.fromRequest(request));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/confirm")
    public ResponseEntity<Void> confirmRegistration(@Valid @RequestBody ConfirmRegistrationRequest request) {
        securityService.confirmRegistration(ConfirmationParameters.fromRequest(request));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(CurrentUser currentUser) {
        return ResponseEntity.ok(currentUser.toString());
    }
}
