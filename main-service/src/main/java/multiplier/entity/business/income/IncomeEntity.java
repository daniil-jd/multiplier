package multiplier.entity.business.income;

import ru.example.common.dto.business.income.IncomeCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.example.common.dto.business.income.IncomeResponseDto;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "income")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncomeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Enumerated(EnumType.ORDINAL)
    private IncomeCategory category;
    private String description;
    private boolean type;
    private long cost;
    private Timestamp created;

    public IncomeResponseDto toDto() {
        return new IncomeResponseDto(name, category, description, type, cost, created);
    }
}
