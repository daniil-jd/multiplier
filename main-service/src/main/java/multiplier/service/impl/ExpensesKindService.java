package multiplier.service.impl;

import ru.example.common.dto.business.expenses_kind.ExpensesCategory;
import lombok.RequiredArgsConstructor;
import ru.example.common.dto.business.expenses_kind.ExpensesKindRequestDto;
import ru.example.common.dto.business.expenses_kind.ExpensesKindResponseDto;
import multiplier.entity.business.expenses.ExpensesKindEntity;
import multiplier.exception.expenses_type.ExpensesTypeAlreadyExistException;
import multiplier.exception.expenses_type.IllegalExpensesPurposeException;
import multiplier.repository.ExpensesKindRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ExpensesKindService {
    private final ExpensesKindRepository repository;

    public List<ExpensesKindResponseDto> getAllExpensesTypeDto() {
        return repository.findAll()
                .stream()
                .map(e -> new ExpensesKindResponseDto(e.getKind(), e.getCategory()))
                .collect(Collectors.toList());
    }

    public ExpensesKindResponseDto save(ExpensesKindRequestDto dto) {
        if (repository.findById(dto.getName()).isPresent()) {
            throw new ExpensesTypeAlreadyExistException("api.exception.expenses.type.already_exist.message");
        }

        checkPurposeString(dto.getPurpose());

        return repository.save(
                new ExpensesKindEntity(
                        dto.getName(),
                        null,
                        ExpensesCategory.valueOf(dto.getPurpose())
                )
        ).toDto();
    }

    private void checkPurposeString(String purposeString) {
        try {
            ExpensesCategory.valueOf(purposeString);
        } catch (IllegalArgumentException e) {
            throw new IllegalExpensesPurposeException(purposeString);
        }
    }

    public Optional<ExpensesKindEntity> findByExpensesKindName(String id) {
        return repository.findById(id);
    }

    public List<ExpensesKindEntity> findExpensesTypeByCategory(String category) {
        try {
            ExpensesCategory.valueOf(category);
        } catch (IllegalArgumentException e) {
            return new ArrayList<>();
        }
        return repository.findAllByCategory(ExpensesCategory.valueOf(category));
    }

    public int getOrdinalOfExpensesPurpose(String purpose) {
        checkPurposeString(purpose);
        return ExpensesCategory.valueOf(purpose).ordinal();
    }
}
