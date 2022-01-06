package ru.example.common.dto.business.income;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncomeRequestDto {
    @NotNull
    private String name;
    @NotNull
    private IncomeCategory category;
    private String description;
    private boolean type;
    @NotNull
    private long cost;
}
