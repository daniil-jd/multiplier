package ru.pet.multiplier.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.pet.multiplier.dto.business.expenses.*;
import ru.pet.multiplier.entity.business.expenses.ExpensesEntity;
import ru.pet.multiplier.entity.business.expenses.ExpensesTypeEntity;
import ru.pet.multiplier.exception.expenses_type.ExpensesTypeDoesNotExistException;
import ru.pet.multiplier.exception.expenses_type.IncorrectFilterForExpensesException;
import ru.pet.multiplier.repository.ExpensesRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ExpensesService {
    private final ExpensesRepository expensesRepository;
    private final ExpensesTypeService expensesTypeService;

    public List<ExpensesResponseDto> getAllExpensesDto() {
        return expensesRepository.findAll()
                .stream().map(e -> new ExpensesResponseDto(
                                e.getExpensesType(),
                                e.getDescription(),
                                e.getCost(),
                                e.isPaymentType(),
                                e.getCreated()
                        )
                ).collect(Collectors.toList());
    }

    public ExpensesResponseDto save(ExpensesRequestDto requestDto) {
        Optional<ExpensesTypeEntity> expensesType = expensesTypeService.findByExpensesTypeName(requestDto.getExpensesType());
        if (expensesType.isEmpty()) {
            throw new ExpensesTypeDoesNotExistException(requestDto.getExpensesType());
        }

        return expensesRepository.save(new ExpensesEntity(
                0,
                expensesType.get(),
                requestDto.getDescription(),
                requestDto.getCost(),
                requestDto.isPaymentType(),
                requestDto.getCreated()
        )).toDto();
    }

    public ExpensesCalculateResponseDto calculateExpenses(ExpensesCalculateRequestDto expensesCalculateRequest) {
        var from = Timestamp.from(Instant.ofEpochMilli(1546300800));
        var till = Timestamp.valueOf(LocalDateTime.now());

        if (expensesCalculateRequest.getPeriod() != null && !StringUtils.isEmpty(expensesCalculateRequest.getPeriod().getFrom())) {
            from = Timestamp.valueOf(expensesCalculateRequest.getPeriod().getFrom());
        }

        if (expensesCalculateRequest.getPeriod() != null && !StringUtils.isEmpty(expensesCalculateRequest.getPeriod().getTill())) {
            till = Timestamp.valueOf(expensesCalculateRequest.getPeriod().getTill());
        }
        var period = new PeriodExpenses(from.toString(), till.toString());
        expensesCalculateRequest.setPeriod(period);

        if (expensesTypeService.findByExpensesTypeName(expensesCalculateRequest.getFilterBy()).isPresent()) {
            return calculateExpensesByType(expensesCalculateRequest);
        } else if (!expensesTypeService.findExpensesTypeByPurpose(expensesCalculateRequest.getFilterBy()).isEmpty()) {
            return calculateExpensesByPurpose(expensesCalculateRequest);
        }

        throw new IncorrectFilterForExpensesException(expensesCalculateRequest.getFilterBy());
    }

    public ExpensesCalculateResponseDto calculateExpensesByPurpose(ExpensesCalculateRequestDto expensesCalculateRequest) {
        var expenses = expensesRepository.getExpensesByPeriodAndPurpose(
                expensesTypeService.getOrdinalOfExpensesPurpose(expensesCalculateRequest.getFilterBy()),
                Timestamp.valueOf(expensesCalculateRequest.getPeriod().getFrom()),
                Timestamp.valueOf(expensesCalculateRequest.getPeriod().getTill())
        );

        return calculateExpensesDto(expenses, expensesCalculateRequest);
    }

    public ExpensesCalculateResponseDto calculateExpensesByType(ExpensesCalculateRequestDto expensesCalculateRequest) {
        var expenses = expensesRepository.getExpensesByPeriodAndType(
                expensesCalculateRequest.getFilterBy(),
                Timestamp.valueOf(expensesCalculateRequest.getPeriod().getFrom()),
                Timestamp.valueOf(expensesCalculateRequest.getPeriod().getTill())
        );

        return calculateExpensesDto(expenses, expensesCalculateRequest);
    }

    private ExpensesCalculateResponseDto calculateExpensesDto(List<ExpensesEntity> expenses, ExpensesCalculateRequestDto expensesCalculateRequest) {
        ExpensesCalculateResponseDto result = new ExpensesCalculateResponseDto();
        result.setFilterBy(expensesCalculateRequest.getFilterBy());
        result.setPeriod(expensesCalculateRequest.getPeriod());
        if (!expenses.isEmpty()) {
            long sum = 0;
            for (ExpensesEntity expense : expenses) {
                result.getCosts().add(expense.getCost());
                result.getDates().add(expense.getCreated());
                sum += expense.getCost();
            }
            result.setTotalCost(sum);
        }

        return result;
    }
}
