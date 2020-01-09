package ru.pet.multiplier.dto.business.income;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pet.multiplier.entity.business.income.IncomeCategory;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncomeResponseDto {
    private String name;
    private IncomeCategory category;
    private String description;
    private boolean type;
    private long cost;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Timestamp created;
}
