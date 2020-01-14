package com.skysoft.friends.web.controller;

import com.skysoft.friends.bussines.api.SecurityService;
import com.skysoft.friends.bussines.common.RegistrationParameters;
import com.skysoft.friends.web.common.request.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class SecurityController {

    @Autowired
    private SecurityService securityService;

    @PostMapping("/registration")
    public ResponseEntity<Void> registration(@Valid @RequestBody RegistrationRequest request) {
        securityService.registerUser(new RegistrationParameters(request.getUserName(), request.getEmail(), request.getPassword()));
        return ResponseEntity.ok().build();
    }
}
