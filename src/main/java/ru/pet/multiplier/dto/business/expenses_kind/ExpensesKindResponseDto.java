package ru.pet.multiplier.dto.business.expenses_kind;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pet.multiplier.entity.business.expenses.ExpensesCategory;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpensesKindResponseDto {
    private String kind;
    private ExpensesCategory category;
}
