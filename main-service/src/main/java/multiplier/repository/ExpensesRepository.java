package multiplier.repository;

import multiplier.entity.business.expenses.ExpensesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface ExpensesRepository extends JpaRepository<ExpensesEntity, Long> {
    @Query(value = "SELECT * FROM MULTIPLIER.EXPENSES WHERE CREATED >= :fromT and CREATED <= :tillT and EXPENSES_KIND_KIND = :expensesKind", nativeQuery = true)
    List<ExpensesEntity> getExpensesByPeriodAndKind(@Param("expensesKind") String expensesType, @Param("fromT") Timestamp fromT, @Param("tillT") Timestamp tillT);

    @Query(value = "SELECT ID, NAME, EXPENSES_KIND_KIND, DESCRIPTION, COST, PAYMENT_TYPE, CREATED FROM MULTIPLIER.EXPENSES e join MULTIPLIER.EXPENSES_KIND ek on e.EXPENSES_KIND_KIND = ek.KIND WHERE e.CREATED >= :fromT and e.CREATED <= :tillT and ek.CATEGORY = :category", nativeQuery = true)
    List<ExpensesEntity> getExpensesByPeriodAndCategory(@Param("category") int expensesKind, @Param("fromT") Timestamp fromT, @Param("tillT") Timestamp tillT);

    @Query(value = "SELECT * FROM MULTIPLIER.EXPENSES WHERE CREATED >= :fromT and CREATED <= :tillT and NAME = :name", nativeQuery = true)
    List<ExpensesEntity> getExpensesByPeriodAndName(@Param("name") String name, @Param("fromT") Timestamp fromT, @Param("tillT") Timestamp tillT);

    List<ExpensesEntity> findAllByName(String name);

    List<ExpensesEntity> findAllByCreatedBetween(Timestamp from, Timestamp till);
}
