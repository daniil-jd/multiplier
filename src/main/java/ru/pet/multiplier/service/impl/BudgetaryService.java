package ru.pet.multiplier.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pet.multiplier.dto.business.budget.BudgetResponseDto;
import ru.pet.multiplier.dto.business.income.IncomeResponseDto;

import java.sql.Timestamp;

@Service
@Transactional
@RequiredArgsConstructor
public class BudgetaryService {
    private final IncomeService incomeService;
    private final ExpensesService expensesService;

    public BudgetResponseDto getBudgetByPeriod(Timestamp from, Timestamp till) {
        long incomes = incomeService.calculateIncomeByPeriod(from, till);
        long expenses = expensesService.calculateExpensesByPeriod(from, till);
        

        return null;
    }
}
