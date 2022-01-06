package multiplier.exception.user;

import multiplier.exception.DefaultException;

public class BadCredentialsException extends DefaultException {
    public BadCredentialsException(String message) {
        super(message);
    }

    public String getCode() {
        return "BAD_CREDENTIALS";
    }
}
