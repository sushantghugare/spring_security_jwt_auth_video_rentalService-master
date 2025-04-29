package com.crio.Spring_security_jwt_auth_Video_rentalService.controller.exchanges.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
  private final String message = "Success";
  private String accessToken;
}