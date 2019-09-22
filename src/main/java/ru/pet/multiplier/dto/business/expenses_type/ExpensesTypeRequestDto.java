package ru.pet.multiplier.dto.business.expenses_type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpensesTypeRequestDto {
    @NotNull
    private String name;
    @NotNull
    private String purpose;
}
