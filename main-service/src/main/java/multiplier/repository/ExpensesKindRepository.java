package multiplier.repository;


import ru.example.common.dto.business.expenses_kind.ExpensesCategory;
import multiplier.entity.business.expenses.ExpensesKindEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpensesKindRepository extends JpaRepository<ExpensesKindEntity, String> {
    List<ExpensesKindEntity> findAllByCategory(ExpensesCategory category);
}
