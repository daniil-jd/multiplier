package multiplier.exception.user;

import multiplier.exception.DefaultException;

public class UserAlreadyEnabledException extends DefaultException {
    public UserAlreadyEnabledException(String message) {
        super(message);
    }

    public String getCode() {
        return "AUTHENTICATION_TOKEN_NOT_FOUND";
    }
}
