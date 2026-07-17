package com.kinetix.workflow.service.impl;

import com.kinetix.workflow.dto.LoginRequest;
import com.kinetix.workflow.dto.LoginResponse;
import com.kinetix.workflow.security.JwtUtils;
import com.kinetix.workflow.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    public LoginResponse login(LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()
                        )
                );

        String token = jwtUtils.generateJwtToken(authentication);

        return new LoginResponse(token);
    }
}