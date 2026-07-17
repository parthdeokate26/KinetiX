package com.kinetix.workflow.service;

import com.kinetix.workflow.dto.LoginRequest;
import com.kinetix.workflow.dto.LoginResponse;

public interface AuthenticationService {

    LoginResponse login(LoginRequest request);

}