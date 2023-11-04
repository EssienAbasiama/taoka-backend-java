package com.chatapp.taoka.Services;

import com.chatapp.taoka.dao.request.SigninRequest;
import com.chatapp.taoka.dao.request.SignupRequest;
import com.chatapp.taoka.dao.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignupRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);
}