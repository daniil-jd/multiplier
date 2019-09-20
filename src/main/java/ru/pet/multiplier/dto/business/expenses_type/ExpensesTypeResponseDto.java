package ru.pet.multiplier.dto.business.expenses_type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pet.multiplier.entity.business.expenses.ExpensesPurpose;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpensesTypeResponseDto {
    private String name;
    private ExpensesPurpose purpose;
}
