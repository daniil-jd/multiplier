package ru.example.common.dto.business.expenses_kind;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpensesKindRequestDto {
    @NotNull
    private String name;
    @NotNull
    private String purpose;
}
