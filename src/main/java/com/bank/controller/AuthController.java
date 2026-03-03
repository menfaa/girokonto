package com.bank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.middleware.security.JwtResponse;
import com.bank.middleware.security.JwtUtil;
import com.bank.middleware.security.LoginRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
        // Beispiel: Benutzername und Passwort prüfen (hier Dummy-Check)
        if ("testuser".equals(loginRequest.getUsername()) && "geheim".equals(loginRequest.getPassword())) {
            String token = JwtUtil.generateToken(loginRequest.getUsername(), "USER");
            return ResponseEntity.ok(new JwtResponse(token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}