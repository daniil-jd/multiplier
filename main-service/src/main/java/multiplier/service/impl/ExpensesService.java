package multiplier.service.impl;

import ru.example.common.dto.business.expenses_kind.ExpensesKindDto;
import lombok.RequiredArgsConstructor;
import ru.example.common.dto.business.expenses.ExpensesCalculateRequestDto;
import ru.example.common.dto.business.expenses.ExpensesCalculateResponseDto;
import ru.example.common.dto.business.expenses.ExpensesRequestDto;
import ru.example.common.dto.business.expenses.ExpensesResponseDto;
import ru.example.common.dto.business.expenses.Info;
import ru.example.common.dto.business.expenses.PeriodExpenses;
import multiplier.entity.business.expenses.ExpensesEntity;
import multiplier.entity.business.expenses.ExpensesKindEntity;
import multiplier.exception.expenses_type.ExpensesTypeDoesNotExistException;
import multiplier.exception.expenses_type.IncorrectFilterForExpensesException;
import multiplier.repository.ExpensesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
                                new ExpensesKindDto(
                                        e.getExpensesKind().getKind(),
                                        e.getExpensesKind().getIcon(),
                                        e.getExpensesKind().getCategory()),
                                e.getDescription(),
                                e.getCost(),
                                e.isPaymentType(),
                                e.getCreated()
                        )
                ).collect(Collectors.toList());
    }

    public List<ExpensesResponseDto> getAllExpensesInPeriod(String dateFrom, String dateTo) {
        Timestamp from = DataService.parseTimestamp(dateFrom, true);
        Timestamp till = DataService.parseTimestamp(dateTo, false);
        return getAllIncomeInPeriod(from, till);
    }

    public List<ExpensesResponseDto> getAllIncomeInPeriod(Timestamp from, Timestamp till) {
        Timestamp fromT = Timestamp.from(Instant.ofEpochMilli(1546300800));
        Timestamp tillT = Timestamp.valueOf(LocalDateTime.now());

        if (from != null) {
            fromT = from;
        }

        if (till != null) {
            tillT = till;
        }

        return expensesRepository.findAllByCreatedBetween(fromT, tillT)
                .stream().map(e -> new ExpensesResponseDto(
                        e.getName(),
                        new ExpensesKindDto(
                                e.getExpensesKind().getKind(),
                                e.getExpensesKind().getIcon(),
                                e.getExpensesKind().getCategory()),
                        e.getDescription(),
                        e.getCost(),
                        e.isPaymentType(),
                        e.getCreated()
                ))
                .collect(Collectors.toList());
    }

    public ExpensesResponseDto save(ExpensesRequestDto requestDto) {
        Optional<ExpensesKindEntity> expensesType = expensesKindService.findByExpensesKindName(requestDto.getExpensesKind());
        if (!expensesType.isPresent()) {
            throw new ExpensesTypeDoesNotExistException(requestDto.getExpensesKind());
        }

        return expensesRepository.save(new ExpensesEntity(
                0,
                requestDto.getName(),
                expensesType.get(),
                requestDto.getDescription(),
                requestDto.getCost(),
                requestDto.getPaymentType(),
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
        List<ExpensesEntity> expenses = expensesRepository.getExpensesByPeriodAndCategory(
                expensesKindService.getOrdinalOfExpensesPurpose(expensesCalculateRequest.getFilterBy()),
                expensesCalculateRequest.getPeriod().getFrom(),
                expensesCalculateRequest.getPeriod().getTill()
        );

        return calculateExpensesDto(expenses, expensesCalculateRequest);
    }

    public ExpensesCalculateResponseDto calculateExpensesByKind(ExpensesCalculateRequestDto expensesCalculateRequest) {
        List<ExpensesEntity> expenses = expensesRepository.getExpensesByPeriodAndKind(
                expensesCalculateRequest.getFilterBy(),
                expensesCalculateRequest.getPeriod().getFrom(),
                expensesCalculateRequest.getPeriod().getTill()
        );

        return calculateExpensesDto(expenses, expensesCalculateRequest);
    }

    public ExpensesCalculateResponseDto calculateExpensesByName(ExpensesCalculateRequestDto expensesCalculateRequest) {
        List<ExpensesEntity> expenses = expensesRepository.getExpensesByPeriodAndName(
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
        Timestamp from = Timestamp.from(Instant.ofEpochMilli(1546300800));
        Timestamp till = Timestamp.valueOf(LocalDateTime.now());

        if (expensesCalculateRequest.getPeriod() != null && !StringUtils.isEmpty(expensesCalculateRequest.getPeriod().getFrom())) {
            from = expensesCalculateRequest.getPeriod().getFrom();
        }

        if (expensesCalculateRequest.getPeriod() != null && !StringUtils.isEmpty(expensesCalculateRequest.getPeriod().getTill())) {
            till = expensesCalculateRequest.getPeriod().getTill();
        }
        PeriodExpenses period = new PeriodExpenses(from, till);
        expensesCalculateRequest.setPeriod(period);

        return expensesCalculateRequest;
    }
}
