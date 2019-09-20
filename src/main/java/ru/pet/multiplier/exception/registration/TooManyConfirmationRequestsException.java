package ru.pet.multiplier.exception.registration;

import ru.pet.multiplier.exception.DefaultException;

public class TooManyConfirmationRequestsException extends DefaultException {
    public TooManyConfirmationRequestsException(String message) {
        super(message);
    }

    public String getCode() {
        return "AUTHENTICATION_TOKEN_NOT_FOUND";
    }
}
