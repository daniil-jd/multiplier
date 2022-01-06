package multiplier.exception.expenses_type;

import multiplier.exception.DefaultException;

public class IncorrectFilterForExpensesException extends DefaultException {
    public IncorrectFilterForExpensesException(String message) {
        super(message);
    }

    public String getCode() {
        return "INCORRECT_FILTER_FOR_EXPENSES";
    }
}
