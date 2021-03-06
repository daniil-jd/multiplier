package ru.pet.multiplier.dto.business.expenses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pet.multiplier.entity.business.expenses.ExpensesKindEntity;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpensesResponseDto {
    private String name;
    private ExpensesKindEntity expensesKind;
    private String description;
    private long cost;
    private boolean paymentType;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Timestamp created;
}
