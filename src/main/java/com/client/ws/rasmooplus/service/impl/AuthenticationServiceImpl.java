package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.dto.LoginDto;
import com.client.ws.rasmooplus.dto.TokenDto;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.repository.UserCredentialsRepository;
import com.client.ws.rasmooplus.service.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final UserCredentialsRepository userCredentialsRepository;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, JwtService jwtService, UserCredentialsRepository userCredentialsRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userCredentialsRepository = userCredentialsRepository;
    }

    @Override
    public TokenDto authenticate(LoginDto dto) {

        authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(dto.username(), dto.password()));

        var user = userCredentialsRepository.findByUsername(dto.username())
                .orElseThrow(() -> new NotFoundException("Credenciais do usuário não encontradas"));

        String token = jwtService.generateToken(user);

        return new TokenDto(token, "Bearer", jwtService.getExpiration());

    }
}
