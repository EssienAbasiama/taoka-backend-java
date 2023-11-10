package com.chatapp.taoka.Controller;

import com.chatapp.taoka.Services.impl.AuthenticationServiceImpl;
import com.chatapp.taoka.dao.request.SigninRequest;
import com.chatapp.taoka.dao.request.SignupRequest;
import com.chatapp.taoka.dao.response.JwtAuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthenticationServiceImpl authService;

    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        try {
            JwtAuthenticationResponse response = authService.signup(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody SigninRequest request){
        return ResponseEntity.ok(authService.signin(request));
    }



}