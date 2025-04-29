package com.crio.Spring_security_jwt_auth_Video_rentalService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.crio.Spring_security_jwt_auth_Video_rentalService.controller.exchanges.request.AuthRequest;
import com.crio.Spring_security_jwt_auth_Video_rentalService.controller.exchanges.request.RegisterRequest;
import com.crio.Spring_security_jwt_auth_Video_rentalService.controller.exchanges.response.AuthResponse;
import com.crio.Spring_security_jwt_auth_Video_rentalService.service.AuthService;

@Controller
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

}
