package ru.multiplier.controller.v1

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/expense")
class ExpenseController {

    @PostMapping
    fun createExpense() {

    }


}