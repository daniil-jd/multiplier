package ru.pet.multiplier.dto.business.expenses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pet.multiplier.entity.business.expenses.ExpensesTypeEntity;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpensesResponseDto {
    private ExpensesTypeEntity expensesType;
    private String description;
    private long cost;
    private boolean paymentType;
    private Timestamp created;
}
