package ru.pet.multiplier.dto.business.expenses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeriodExpenses {
    private String from;
    private String till;
}
