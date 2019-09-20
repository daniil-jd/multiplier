package ru.pet.multiplier.exception.user;

import ru.pet.multiplier.exception.DefaultException;

public class BadCredentialsException extends DefaultException {
    public BadCredentialsException(String message) {
        super(message);
    }

    public String getCode() {
        return "BAD_CREDENTIALS";
    }
}
