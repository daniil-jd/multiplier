package ru.example.common.dto.business.expenses;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.example.common.dto.business.expenses_kind.ExpensesKindDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpensesResponseDto {
    private String name;
    private ExpensesKindDto expensesKind;
    private String description;
    private long cost;
    private boolean paymentType;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Timestamp created;
}
