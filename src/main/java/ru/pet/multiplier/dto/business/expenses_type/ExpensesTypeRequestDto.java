package ru.pet.multiplier.dto.business.expenses_type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpensesTypeRequestDto {
    private String name;
    private String purpose;
}
