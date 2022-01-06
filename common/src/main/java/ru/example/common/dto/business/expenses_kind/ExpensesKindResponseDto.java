package ru.example.common.dto.business.expenses_kind;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpensesKindResponseDto {
    private String kind;
    private ExpensesCategory category;
}
