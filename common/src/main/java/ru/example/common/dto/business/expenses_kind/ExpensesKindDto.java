package ru.example.common.dto.business.expenses_kind;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpensesKindDto {
    private String kind;
    private String icon;
    private ExpensesCategory category;
}
