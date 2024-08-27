package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.dto.LoginDto;
import com.client.ws.rasmooplus.dto.TokenDto;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.model.redis.UserRecoveryCode;
import com.client.ws.rasmooplus.repository.UserCredentialsRepository;
import com.client.ws.rasmooplus.repository.redis.UserRecoveryCodeRepository;
import com.client.ws.rasmooplus.service.AuthenticationService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    private static final Random RANDOM = new Random();

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final UserCredentialsRepository userCredentialsRepository;

    private final UserRecoveryCodeRepository userRecoveryCodeRepository;

    public AuthenticationServiceImpl(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            UserCredentialsRepository userCredentialsRepository,
            UserRecoveryCodeRepository userRecoveryCodeRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userCredentialsRepository = userCredentialsRepository;
        this.userRecoveryCodeRepository = userRecoveryCodeRepository;
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

    @Override
    public void sendRecoveryCode(String email) {

        String code = String.format("%04d", RANDOM.nextInt(10000));

        UserRecoveryCode userRecoveryCode;

        var userRecoveryCodeOpt = userRecoveryCodeRepository.findByEmail(email);

        if (userRecoveryCodeOpt.isEmpty()) {

            var user = userCredentialsRepository.findByUsername(email);

            if (user.isEmpty()) {
                throw new EntityNotFoundException("Usuário não encontrado");
            }

            userRecoveryCode = new UserRecoveryCode();
            userRecoveryCode.setEmail(email);

        } else {
            userRecoveryCode = userRecoveryCodeOpt.get();
        }

        userRecoveryCode.setCode(code);
        userRecoveryCode.setCreationDate(LocalDateTime.now());

        userRecoveryCodeRepository.save(userRecoveryCode);
        LOGGER.info("Código de recuperação de conta: {}", code);

    }

}
