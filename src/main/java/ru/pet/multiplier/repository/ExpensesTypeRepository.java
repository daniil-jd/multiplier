package ru.pet.multiplier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pet.multiplier.entity.business.expenses.ExpensesTypeEntity;

public interface ExpensesTypeRepository extends JpaRepository<ExpensesTypeEntity, String> {
}
