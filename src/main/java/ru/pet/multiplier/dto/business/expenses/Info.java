package ru.pet.multiplier.dto.business.expenses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pet.multiplier.entity.business.expenses.ExpensesKindEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Info {
    private String name;
    private String kind;
    private String category;

}
