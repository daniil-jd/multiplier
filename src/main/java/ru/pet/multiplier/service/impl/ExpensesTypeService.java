package ru.pet.multiplier.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pet.multiplier.dto.business.expenses_type.ExpensesTypeRequestDto;
import ru.pet.multiplier.dto.business.expenses_type.ExpensesTypeResponseDto;
import ru.pet.multiplier.entity.business.expenses.ExpensesPurpose;
import ru.pet.multiplier.entity.business.expenses.ExpensesTypeEntity;
import ru.pet.multiplier.exception.expenses_type.ExpensesTypeAlreadyExistException;
import ru.pet.multiplier.repository.ExpensesTypeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ExpensesTypeService {
    private final ExpensesTypeRepository repository;

    public List<ExpensesTypeResponseDto> getAllExpensesTypeDto() {
        return repository.findAll()
                .stream()
                .map(e -> new ExpensesTypeResponseDto(e.getName(), e.getPurpose()))
                .collect(Collectors.toList());
    }

    public ExpensesTypeResponseDto save(ExpensesTypeRequestDto dto) {
        if (repository.findById(dto.getName()).isPresent()) {
            throw new ExpensesTypeAlreadyExistException("api.exception.expenses.type.already_exist.message");
        }

        return repository.save(
                new ExpensesTypeEntity(
                        dto.getName(),
                        null,
                        ExpensesPurpose.valueOf(dto.getPurpose())
                )
        ).toDto();
    }
}
