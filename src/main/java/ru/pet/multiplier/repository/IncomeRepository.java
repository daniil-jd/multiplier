package ru.pet.multiplier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pet.multiplier.entity.business.income.IncomeEntity;

import java.sql.Timestamp;
import java.util.List;

public interface IncomeRepository extends JpaRepository<IncomeEntity, Long> {
    List<IncomeEntity> findAllByCreatedBetween(Timestamp from, Timestamp till);
}
