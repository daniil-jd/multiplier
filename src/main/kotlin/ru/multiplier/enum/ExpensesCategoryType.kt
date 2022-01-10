package ru.multiplier.enum

enum class ExpensesCategoryType {
    SHOP, ENTERTAINMENT, RENT, TICKET, FOOD, BOOK, WORK_ENVIRONMENT;

    companion object {
        fun from(value: String?): ExpensesCategoryType? {
            if (value == null) return null
            return values().firstOrNull { it.toString().lowercase() == value.lowercase() }
        }
    }
}