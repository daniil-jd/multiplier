package multiplier.exception.authentication;

import multiplier.exception.DefaultException;

public class AuthenticateTokenException extends DefaultException {
    public AuthenticateTokenException(String message) {
        super(message);
    }

    public String getCode() {
        return "INVALID_TOKEN";
    }
}
