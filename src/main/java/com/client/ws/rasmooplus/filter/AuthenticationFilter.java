package com.client.ws.rasmooplus.filter;

import com.client.ws.rasmooplus.service.impl.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Configuration
public class AuthenticationFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String BEARER_TOKEN = "Bearer ";

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    public AuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader(AUTHORIZATION_HEADER);

        if (Objects.isNull(authHeader) || !authHeader.startsWith(BEARER_TOKEN)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring(BEARER_TOKEN.length());
        final String username = jwtService.extractUsername(token);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.nonNull(username) && Objects.isNull(authentication)) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtService.isTokenValid(token, userDetails)) {

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        }

        filterChain.doFilter(request, response);
    }

}
