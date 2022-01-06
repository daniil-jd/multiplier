package multiplier.exception.user;

import multiplier.exception.DefaultException;

public class UsernameAlreadyExistsException  extends DefaultException {
    public UsernameAlreadyExistsException(String username) {
        super(username);
    }

    public String getCode() {
        return "AUTHENTICATION_TOKEN_NOT_FOUND";
    }
}
