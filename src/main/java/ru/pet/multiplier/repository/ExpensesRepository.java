package ru.pet.multiplier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.pet.multiplier.entity.business.expenses.ExpensesEntity;
import ru.pet.multiplier.entity.business.expenses.ExpensesTypeEntity;

import java.sql.Timestamp;
import java.util.List;

public interface ExpensesRepository extends JpaRepository<ExpensesEntity, Long> {
    @Query(value = "select * from MULTIPLIER.EXPENSES where CREATED >= :fromT and CREATED <= :tillT and EXPENSES_TYPE_NAME = :expensesType", nativeQuery = true)
    List<ExpensesEntity> getExpensesByPeriodAndType(String expensesType, Timestamp fromT, Timestamp tillT);

    //select id, cost, created, DESCRIPTION, PAYMENT_TYPE, EXPENSES_TYPE_NAME from MULTIPLIER.EXPENSES e join MULTIPLIER.EXPENSES_TYPE et on e.EXPENSES_TYPE_NAME = et.NAME where et.PURPOSE = 0
    @Query(value = "select id, cost, created, DESCRIPTION, PAYMENT_TYPE, EXPENSES_TYPE_NAME from MULTIPLIER.EXPENSES e join MULTIPLIER.EXPENSES_TYPE et on e.EXPENSES_TYPE_NAME = et.NAME where e.CREATED >= :fromT and e.CREATED <= :tillT and et.PURPOSE = :expensesPurpose", nativeQuery = true)
    List<ExpensesEntity> getExpensesByPeriodAndPurpose(int expensesPurpose, Timestamp fromT, Timestamp tillT);

}
