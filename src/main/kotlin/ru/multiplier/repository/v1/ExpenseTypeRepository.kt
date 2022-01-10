package ru.multiplier.repository.v1

import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class ExpenseTypeRepository(
    private val dsl: DSLContext
) {

//    fun create(name: String): Ex

}