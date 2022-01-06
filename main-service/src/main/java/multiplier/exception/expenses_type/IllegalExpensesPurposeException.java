package multiplier.exception.expenses_type;

import multiplier.exception.DefaultException;

public class IllegalExpensesPurposeException extends DefaultException {
    public IllegalExpensesPurposeException(String message) {
        super(message);
    }

    public String getCode() {
        return "ILLEGAL_EXPENSES_PURPOSE";
    }
}
