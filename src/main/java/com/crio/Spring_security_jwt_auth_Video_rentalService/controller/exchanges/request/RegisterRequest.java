package com.crio.Spring_security_jwt_auth_Video_rentalService.controller.exchanges.request;

import com.crio.Spring_security_jwt_auth_Video_rentalService.model.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private Role role;
}