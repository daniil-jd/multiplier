package ru.pet.multiplier.entity.business.expenses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "expenses_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpensesTypeEntity {
    @Id
    private String name;
    private String icon;
    @Enumerated(EnumType.ORDINAL)
    private ExpensesPurpose purpose;
}
