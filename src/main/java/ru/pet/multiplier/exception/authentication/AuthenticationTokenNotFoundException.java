package ru.pet.multiplier.exception.authentication;

import ru.pet.multiplier.exception.DefaultException;

public class AuthenticationTokenNotFoundException extends DefaultException {
    public AuthenticationTokenNotFoundException(String message) {
        super(message);
    }

    public String getCode() {
        return "AUTHENTICATION_TOKEN_NOT_FOUND";
    }
}
