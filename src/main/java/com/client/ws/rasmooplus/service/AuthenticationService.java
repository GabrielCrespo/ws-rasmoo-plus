package com.client.ws.rasmooplus.service;

import com.client.ws.rasmooplus.dto.LoginDto;
import com.client.ws.rasmooplus.dto.TokenDto;
import com.client.ws.rasmooplus.dto.UserCredentialsDto;

public interface AuthenticationService {

    TokenDto authenticate(LoginDto dto);

    void sendRecoveryCode(String email);

    boolean recoveryCodeIsValid(String recoveryCode, String email);

    void updatePasswordByRecoveryCode(UserCredentialsDto dto);

}
