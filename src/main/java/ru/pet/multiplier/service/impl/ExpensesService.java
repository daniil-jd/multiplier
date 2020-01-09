package ru.pet.multiplier.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.pet.multiplier.dto.business.expenses.ExpensesCalculateRequestDto;
import ru.pet.multiplier.dto.business.expenses.ExpensesCalculateResponseDto;
import ru.pet.multiplier.dto.business.expenses.ExpensesRequestDto;
import ru.pet.multiplier.dto.business.expenses.ExpensesResponseDto;
import ru.pet.multiplier.dto.business.expenses.Info;
import ru.pet.multiplier.dto.business.expenses.PeriodExpenses;
import ru.pet.multiplier.entity.business.expenses.ExpensesEntity;
import ru.pet.multiplier.entity.business.expenses.ExpensesKindEntity;
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
    private final ExpensesKindService expensesKindService;

    public List<ExpensesResponseDto> getAllExpensesDto() {
        return expensesRepository.findAll()
                .stream().map(e -> new ExpensesResponseDto(
                                e.getName(),
                                e.getExpensesKind(),
                                e.getDescription(),
                                e.getCost(),
                                e.isPaymentType(),
                                e.getCreated()
                        )
                ).collect(Collectors.toList());
    }

    public List<ExpensesResponseDto> getAllIncomeInPeriod(Timestamp from, Timestamp till) {
        var fromT = Timestamp.from(Instant.ofEpochMilli(1546300800));
        var tillT = Timestamp.valueOf(LocalDateTime.now());

        if (from != null) {
            fromT = from;
        }

        if (till != null) {
            tillT = till;
        }

        return expensesRepository.findAllByCreatedBetween(fromT, tillT)
                .stream().map(e -> new ExpensesResponseDto(
                        e.getName(),
                        e.getExpensesKind(),
                        e.getDescription(),
                        e.getCost(),
                        e.isPaymentType(),
                        e.getCreated()
                ))
                .collect(Collectors.toList());
    }

    public ExpensesResponseDto save(ExpensesRequestDto requestDto) {
        Optional<ExpensesKindEntity> expensesType = expensesKindService.findByExpensesKindName(requestDto.getExpensesKind());
        if (expensesType.isEmpty()) {
            throw new ExpensesTypeDoesNotExistException(requestDto.getExpensesKind());
        }

        return expensesRepository.save(new ExpensesEntity(
                0,
                requestDto.getName(),
                expensesType.get(),
                requestDto.getDescription(),
                requestDto.getCost(),
                requestDto.isPaymentType(),
                requestDto.getCreated()
        )).toDto();
    }

    public ExpensesCalculateResponseDto calculateExpenses(ExpensesCalculateRequestDto expensesCalculateRequest) {
        expensesCalculateRequest = setPeriod(expensesCalculateRequest);

        if (expensesKindService.findByExpensesKindName(expensesCalculateRequest.getFilterBy()).isPresent()) {
            return calculateExpensesByKind(expensesCalculateRequest);
        } else if (!expensesKindService.findExpensesTypeByCategory(expensesCalculateRequest.getFilterBy()).isEmpty()) {
            return calculateExpensesByCategory(expensesCalculateRequest);
        } else if (!expensesRepository.findAllByName(expensesCalculateRequest.getFilterBy()).isEmpty()) {
            return calculateExpensesByName(expensesCalculateRequest);
        }

        throw new IncorrectFilterForExpensesException(expensesCalculateRequest.getFilterBy());
    }

    public ExpensesCalculateResponseDto calculateExpensesByCategory(ExpensesCalculateRequestDto expensesCalculateRequest) {
        var expenses = expensesRepository.getExpensesByPeriodAndCategory(
                expensesKindService.getOrdinalOfExpensesPurpose(expensesCalculateRequest.getFilterBy()),
                expensesCalculateRequest.getPeriod().getFrom(),
                expensesCalculateRequest.getPeriod().getTill()
        );

        return calculateExpensesDto(expenses, expensesCalculateRequest);
    }

    public ExpensesCalculateResponseDto calculateExpensesByKind(ExpensesCalculateRequestDto expensesCalculateRequest) {
        var expenses = expensesRepository.getExpensesByPeriodAndKind(
                expensesCalculateRequest.getFilterBy(),
                expensesCalculateRequest.getPeriod().getFrom(),
                expensesCalculateRequest.getPeriod().getTill()
        );

        return calculateExpensesDto(expenses, expensesCalculateRequest);
    }

    public ExpensesCalculateResponseDto calculateExpensesByName(ExpensesCalculateRequestDto expensesCalculateRequest) {
        var expenses = expensesRepository.getExpensesByPeriodAndName(
                expensesCalculateRequest.getFilterBy(),
                expensesCalculateRequest.getPeriod().getFrom(),
                expensesCalculateRequest.getPeriod().getTill()
        );

        return calculateExpensesDto(expenses, expensesCalculateRequest);
    }

    public long calculateExpensesByPeriod(Timestamp from, Timestamp till) {
        long result = 0;
        for (ExpensesResponseDto expense : getAllIncomeInPeriod(from, till)) {
            result += expense.getCost();
        }
        return result;
    }

    private ExpensesCalculateResponseDto calculateExpensesDto(List<ExpensesEntity> expenses, ExpensesCalculateRequestDto expensesCalculateRequest) {
        ExpensesCalculateResponseDto result = new ExpensesCalculateResponseDto();
        result.setFilterBy(expensesCalculateRequest.getFilterBy());
        result.setPeriod(expensesCalculateRequest.getPeriod());
        if (!expenses.isEmpty()) {
            long sum = 0;
            for (ExpensesEntity expense : expenses) {
                if (expensesCalculateRequest.isDetails()) {
                    result.getDetails().getInfo().add(new Info(
                            expense.getName(),
                            expense.getExpensesKind().getKind(),
                            expense.getExpensesKind().getCategory().name()
                    ));
                    result.getDetails().getCosts().add(expense.getCost());
                    result.getDetails().getDates().add(expense.getCreated());
                }
                sum += expense.getCost();
            }
            result.setTotalCost(sum);
        }

        return result;
    }

    private ExpensesCalculateRequestDto setPeriod(ExpensesCalculateRequestDto expensesCalculateRequest) {
        var from = Timestamp.from(Instant.ofEpochMilli(1546300800));
        var till = Timestamp.valueOf(LocalDateTime.now());

        if (expensesCalculateRequest.getPeriod() != null && !StringUtils.isEmpty(expensesCalculateRequest.getPeriod().getFrom())) {
            from = expensesCalculateRequest.getPeriod().getFrom();
        }

        if (expensesCalculateRequest.getPeriod() != null && !StringUtils.isEmpty(expensesCalculateRequest.getPeriod().getTill())) {
            till = expensesCalculateRequest.getPeriod().getTill();
        }
        var period = new PeriodExpenses(from, till);
        expensesCalculateRequest.setPeriod(period);

        return expensesCalculateRequest;
    }
}
