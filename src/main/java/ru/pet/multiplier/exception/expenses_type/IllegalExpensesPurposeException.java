package ru.pet.multiplier.exception.expenses_type;

import ru.pet.multiplier.exception.DefaultException;

public class IllegalExpensesPurposeException extends DefaultException {
    public IllegalExpensesPurposeException(String message) {
        super(message);
    }

    public String getCode() {
        return "ILLEGAL_EXPENSES_PURPOSE";
    }
}
