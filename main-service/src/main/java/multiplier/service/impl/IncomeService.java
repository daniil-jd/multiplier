package multiplier.service.impl;

import lombok.RequiredArgsConstructor;
import ru.example.common.dto.business.income.IncomeRequestDto;
import ru.example.common.dto.business.income.IncomeResponseDto;
import multiplier.entity.business.income.IncomeEntity;
import multiplier.repository.IncomeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class IncomeService {
    private final IncomeRepository repository;

    public List<IncomeResponseDto> getAllIncomeInPeriod(Timestamp from, Timestamp till) {
        Timestamp fromT = Timestamp.from(Instant.ofEpochMilli(1546300800));
        Timestamp tillT = Timestamp.valueOf(LocalDateTime.now());

        if (from != null) {
            fromT = from;
        }

        if (till != null) {
            tillT = till;
        }

        return repository.findAllByCreatedBetween(fromT, tillT)
                .stream().map(i -> new IncomeResponseDto(
                        i.getName(),
                        i.getCategory(),
                        i.getDescription(),
                        i.isType(),
                        i.getCost(),
                        i.getCreated()
                ))
                .collect(Collectors.toList());
    }

    public IncomeResponseDto createIncome(IncomeRequestDto incomeRequestDto) {
        return repository.save(new IncomeEntity(
                0,
                incomeRequestDto.getName(),
                incomeRequestDto.getCategory(),
                incomeRequestDto.getDescription(),
                incomeRequestDto.isType(),
                incomeRequestDto.getCost(),
                Timestamp.valueOf(LocalDateTime.now())
        )).toDto();
    }

    public long calculateIncomeByPeriod(Timestamp from, Timestamp till) {
        long result = 0;
        for (IncomeResponseDto income : getAllIncomeInPeriod(from, till)) {
            result += income.getCost();
        }
        return result;
    }
}
