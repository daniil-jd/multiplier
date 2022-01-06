package ru.example.common.dto.business.expenses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpensesRequestDto {
    @NotNull
    private String name;
    @NotNull
    private String expensesKind;
    private String description;
    @NotNull
    private Long cost;
    private Boolean paymentType;
    @NotNull
    private Timestamp created;
}
