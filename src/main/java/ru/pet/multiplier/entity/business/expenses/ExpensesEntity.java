package ru.pet.multiplier.entity.business.expenses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pet.multiplier.dto.business.expenses.ExpensesResponseDto;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "expenses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpensesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(cascade = {CascadeType.MERGE})
    private ExpensesTypeEntity expensesType;
    private String description;
    private long cost;
    private boolean paymentType;
    private Timestamp created;

    public ExpensesResponseDto toDto() {
        return new ExpensesResponseDto(expensesType, description, cost, paymentType, created);
    }
}
