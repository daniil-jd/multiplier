package multiplier.exception.expenses_type;

import multiplier.exception.DefaultException;

public class ExpensesTypeAlreadyExistException extends DefaultException {
    public ExpensesTypeAlreadyExistException(String message) {
        super(message);
    }

    public String getCode() {
        return "EXPENSES_TYPE_ALREADY_EXIST";
    }
}
