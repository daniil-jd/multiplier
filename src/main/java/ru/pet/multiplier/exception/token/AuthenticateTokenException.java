package ru.pet.multiplier.exception.token;

public class AuthenticateTokenException extends DefaultException {
    public AuthenticateTokenException(String message) {
        super(message);
    }

    public String getCode() {
        return "INVALID_TOKEN";
    }
}
