package ru.pet.multiplier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pet.multiplier.entity.business.expenses.ExpensesCategory;
import ru.pet.multiplier.entity.business.expenses.ExpensesKindEntity;

import java.util.List;

public interface ExpensesKindRepository extends JpaRepository<ExpensesKindEntity, String> {
    List<ExpensesKindEntity> findAllByCategory(ExpensesCategory category);
}
