package ru.example.common.dto.business.expenses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpensesCalculateRequestDto {
    @NotNull
    private String filterBy;
    private boolean details;
    private PeriodExpenses period;
}
