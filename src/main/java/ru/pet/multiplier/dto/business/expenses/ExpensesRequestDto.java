package ru.pet.multiplier.dto.business.expenses;

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
    private String expensesType;
    private String description;
    @NotNull
    private long cost;
    private boolean paymentType;
    @NotNull
    private Timestamp created;
}
