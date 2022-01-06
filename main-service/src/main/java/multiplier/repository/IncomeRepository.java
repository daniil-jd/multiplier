package multiplier.repository;

import multiplier.entity.business.income.IncomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface IncomeRepository extends JpaRepository<IncomeEntity, Long> {
    List<IncomeEntity> findAllByCreatedBetween(Timestamp from, Timestamp till);
}
