package com.client.ws.rasmooplus.controller;

import com.client.ws.rasmooplus.dto.LoginDto;
import com.client.ws.rasmooplus.dto.TokenDto;
import com.client.ws.rasmooplus.model.redis.UserRecoveryCode;
import com.client.ws.rasmooplus.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public ResponseEntity<TokenDto> authenticate(@RequestBody LoginDto dto) {
        return ResponseEntity.ok(authenticationService.authenticate(dto));
    }

    @PostMapping("/recovery-code/send")
    public ResponseEntity<Void> sendRecoveryCode(@RequestBody @Valid UserRecoveryCode code) {
        authenticationService.sendRecoveryCode(code.getEmail());
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/recovery-code")
    public ResponseEntity<Boolean> recoveryCodeIsValid(@RequestParam(value = "recoveryCode") String recoveryCode,
                                                       @RequestParam(value = "email") String email) {
        return ResponseEntity.ok(authenticationService.recoveryCodeIsValid(recoveryCode, email));
    }

}
