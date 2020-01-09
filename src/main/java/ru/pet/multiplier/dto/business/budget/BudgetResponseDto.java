package ru.pet.multiplier.dto.business.budget;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pet.multiplier.dto.business.expenses.PeriodExpenses;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetResponseDto {
    private PeriodExpenses period;
    private long difference;
    private long balance;
    private double percent;
    private int smartCalculate;
}
