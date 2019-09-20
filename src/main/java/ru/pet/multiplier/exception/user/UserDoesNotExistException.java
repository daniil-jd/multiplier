package ru.pet.multiplier.exception.user;

import ru.pet.multiplier.exception.DefaultException;

public class UserDoesNotExistException  extends DefaultException {
    public UserDoesNotExistException(String message) {
        super(message);
    }

    public String getCode() {
        return "AUTHENTICATION_TOKEN_NOT_FOUND";
    }
}
