package ru.pet.multiplier.dto.business.expenses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpensesCalculateRequestDto {
    @NonNull
    private String filterBy;
    private boolean details;
    private PeriodExpenses period;
}
