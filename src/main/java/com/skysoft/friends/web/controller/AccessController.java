package com.skysoft.friends.web.controller;

import com.skysoft.friends.bussines.api.AccessService;
import com.skysoft.friends.bussines.common.RegistrationParameters;
import com.skysoft.friends.web.common.request.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/access")
public class AccessController {

    private final AccessService accessService;

    @PostMapping("/registration")
    public ResponseEntity<Void> registration(@Valid @RequestBody RegistrationRequest request) {
        accessService.registerUser(RegistrationParameters.fromRequest(request));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/confirm")
    public ResponseEntity<Void> confirmRegistration(@RequestParam("token") String token) {
        accessService.confirmRegistration(token);
        return ResponseEntity.ok().build();
    }
}
