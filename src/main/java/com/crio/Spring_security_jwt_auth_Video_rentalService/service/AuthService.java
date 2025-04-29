package com.crio.Spring_security_jwt_auth_Video_rentalService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.crio.Spring_security_jwt_auth_Video_rentalService.controller.exchanges.request.AuthRequest;
import com.crio.Spring_security_jwt_auth_Video_rentalService.controller.exchanges.request.RegisterRequest;
import com.crio.Spring_security_jwt_auth_Video_rentalService.controller.exchanges.response.AuthResponse;
import com.crio.Spring_security_jwt_auth_Video_rentalService.model.User;
import com.crio.Spring_security_jwt_auth_Video_rentalService.model.enums.Role;
import com.crio.Spring_security_jwt_auth_Video_rentalService.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JWTService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (request.getRole() == null) {
            request.setRole(Role.CUSTOMER);
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);
        userRepository.save(user);
        return AuthResponse.builder()
                .accessToken(jwtToken)
                .build();

    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail());
        String jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .accessToken(jwtToken)
                .build();
    }
}
