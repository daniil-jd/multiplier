package ru.pet.multiplier.exception.expenses_type;

import ru.pet.multiplier.exception.DefaultException;

import javax.validation.constraints.NotNull;

public class ExpensesTypeDoesNotExistException extends DefaultException {
    public ExpensesTypeDoesNotExistException(String message) {
        super(message);
    }

    public String getCode() {
        return "EXPENSES_TYPE_DOEST_EXIST";
    }
}
