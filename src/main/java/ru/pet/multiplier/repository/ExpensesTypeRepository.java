package ru.pet.multiplier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pet.multiplier.entity.business.expenses.ExpensesPurpose;
import ru.pet.multiplier.entity.business.expenses.ExpensesTypeEntity;

import java.util.List;

public interface ExpensesTypeRepository extends JpaRepository<ExpensesTypeEntity, String> {
    List<ExpensesTypeEntity> findAllByPurpose(ExpensesPurpose purpose);
}
