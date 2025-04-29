package com.crio.Spring_security_jwt_auth_Video_rentalService.exceptions;

public class VideoNotFoundException extends RuntimeException {
    public VideoNotFoundException(String message) {
        super(message);
    }
}
