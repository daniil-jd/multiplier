package ru.pet.multiplier.dto.business.expenses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.LinkedList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpensesCalculateResponseDto {
    private String filterBy;
    private long totalCost;
    private PeriodExpenses period;
    private LinkedList<Long> costs = new LinkedList<>();
    private LinkedList<Timestamp> dates = new LinkedList<>();
}
