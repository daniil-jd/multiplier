package ru.multiplier.validator

import org.springframework.stereotype.Component
import ru.multiplier.enum.ExpensesCategoryType

@Component
class ExpenseCategoryTypeValidator {

    fun validateCategoryType(categoryType: String): Boolean {
        if (categoryType != null && ExpensesCategoryType.from(categoryType) == null) {
            throw ValidationException("positionSearchOrderType")
        }
    }

}