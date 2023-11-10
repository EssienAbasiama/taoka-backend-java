package com.chatapp.taoka.Services.impl;

import com.chatapp.taoka.Model.Role;
import com.chatapp.taoka.Model.User;
import com.chatapp.taoka.Repository.UserRepository;
import com.chatapp.taoka.Services.AuthenticationService;
import com.chatapp.taoka.Services.JwtService;
import com.chatapp.taoka.dao.request.SigninRequest;
import com.chatapp.taoka.dao.request.SignupRequest;
import com.chatapp.taoka.dao.response.JwtAuthenticationResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public JwtAuthenticationResponse signup(SignupRequest request) {
        validateSignupRequest(request);
        var user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER).createdAt(LocalDateTime.now()).friends(new ArrayList<User>()).friendRequest(new ArrayList<User>()).build();

        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    private void validateSignupRequest(SignupRequest request) {
        if (StringUtils.isEmpty(request.getFirstName())) {
            throw new IllegalArgumentException("firstName is Required");
        }

        if (StringUtils.isEmpty(request.getLastName())) {
            throw new IllegalArgumentException("lastName is required.");
        }

        if (StringUtils.isEmpty(request.getEmail())) {
            throw new IllegalArgumentException("Email is required.");
        } else if (!isValidEmail(request.getEmail())) {
            throw new IllegalArgumentException("Invalid email format.");
        }

        if (StringUtils.isEmpty(request.getPassword())) {
            throw new IllegalArgumentException("Password is required.");
        }
    }
    private boolean isValidEmail(String email) {
        // Implement your email validation logic, e.g., using regex or libraries
        // For simplicity, we use a basic check here
        return email.contains("@") && email.contains(".");
    }
}