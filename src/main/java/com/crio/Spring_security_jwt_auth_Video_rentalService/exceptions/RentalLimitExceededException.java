package com.crio.Spring_security_jwt_auth_Video_rentalService.exceptions;

public class RentalLimitExceededException extends RuntimeException {
    public RentalLimitExceededException(String message) {
        super(message);
    }
}
