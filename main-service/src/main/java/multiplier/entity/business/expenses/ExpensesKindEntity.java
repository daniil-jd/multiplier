package multiplier.entity.business.expenses;

import ru.example.common.dto.business.expenses_kind.ExpensesCategory;
import ru.example.common.dto.business.expenses_kind.ExpensesKindResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "expenses_kind")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpensesKindEntity {
    @Id
    private String kind;
    private String icon;
    @Enumerated(EnumType.ORDINAL)
    private ExpensesCategory category;

    public ExpensesKindResponseDto toDto() {
        return new ExpensesKindResponseDto(kind, category);
    }
}
