package ru.pet.multiplier.dto.business.expenses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeriodExpenses {
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Timestamp from;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Timestamp till;
}
