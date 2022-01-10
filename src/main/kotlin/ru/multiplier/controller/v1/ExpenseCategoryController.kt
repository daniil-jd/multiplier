package ru.multiplier.controller.v1

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.multiplier.dto.v1.ExpenseCategoryDto

@RestController
@RequestMapping("/v1/expense/category")
class ExpenseCategoryController {

    @PostMapping
    fun createExpenseCategory(category: ExpenseCategoryDto): ResponseEntity<*> {

        TODO()
    }


}