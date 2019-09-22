package ru.pet.multiplier.exception.expenses_type;

import ru.pet.multiplier.exception.DefaultException;

public class IncorrectFilterForExpensesException extends DefaultException {
    public IncorrectFilterForExpensesException(String message) {
        super(message);
    }

    public String getCode() {
        return "INCORRECT_FILTER_FOR_EXPENSES";
    }
}
