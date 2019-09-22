package ru.pet.multiplier.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pet.multiplier.dto.business.expenses_type.ExpensesTypeRequestDto;
import ru.pet.multiplier.dto.business.expenses_type.ExpensesTypeResponseDto;
import ru.pet.multiplier.entity.business.expenses.ExpensesPurpose;
import ru.pet.multiplier.entity.business.expenses.ExpensesTypeEntity;
import ru.pet.multiplier.exception.expenses_type.ExpensesTypeAlreadyExistException;
import ru.pet.multiplier.exception.expenses_type.IllegalExpensesPurposeException;
import ru.pet.multiplier.repository.ExpensesTypeRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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

        checkPurposeString(dto.getPurpose());

        return repository.save(
                new ExpensesTypeEntity(
                        dto.getName(),
                        null,
                        ExpensesPurpose.valueOf(dto.getPurpose())
                )
        ).toDto();
    }

    private void checkPurposeString(String purposeString) {
        try {
            ExpensesPurpose.valueOf(purposeString);
        } catch (IllegalArgumentException e) {
            throw new IllegalExpensesPurposeException(purposeString);
        }
    }

    public Optional<ExpensesTypeEntity> findByExpensesTypeName(String id) {
        return repository.findById(id);
    }

    public List<ExpensesTypeEntity> findExpensesTypeByPurpose(String purpose) {
        checkPurposeString(purpose);
        return repository.findAllByPurpose(ExpensesPurpose.valueOf(purpose));
    }

    public int getOrdinalOfExpensesPurpose(String purpose) {
        checkPurposeString(purpose);
        return ExpensesPurpose.valueOf(purpose).ordinal();
    }
}
