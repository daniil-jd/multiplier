package multiplier.exception.expenses_type;

import multiplier.exception.DefaultException;

public class ExpensesTypeDoesNotExistException extends DefaultException {
    public ExpensesTypeDoesNotExistException(String message) {
        super(message);
    }

    public String getCode() {
        return "EXPENSES_TYPE_DOEST_EXIST";
    }
}
