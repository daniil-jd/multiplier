package multiplier.service.impl;

import lombok.RequiredArgsConstructor;
import ru.example.common.dto.business.budget.BudgetResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
