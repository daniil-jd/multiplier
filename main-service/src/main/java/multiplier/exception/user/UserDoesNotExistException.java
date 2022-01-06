package multiplier.exception.user;

import multiplier.exception.DefaultException;

public class UserDoesNotExistException  extends DefaultException {
    public UserDoesNotExistException(String message) {
        super(message);
    }

    public String getCode() {
        return "AUTHENTICATION_TOKEN_NOT_FOUND";
    }
}
