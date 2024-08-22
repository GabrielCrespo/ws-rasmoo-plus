package com.client.ws.rasmooplus.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface TokenService {

    String generateToken(UserDetails userDetails);
}
