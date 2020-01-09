package ru.pet.multiplier.dto.business.expenses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpensesCalculateResponseDto {
    private String filterBy;
    private long totalCost;
    private PeriodExpenses period;
    private DetailsCalculateResponseDto details = new DetailsCalculateResponseDto();
}
