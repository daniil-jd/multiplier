package ru.pet.multiplier.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.pet.multiplier.dto.business.income.IncomeRequestDto;
import ru.pet.multiplier.dto.business.income.IncomeResponseDto;
import ru.pet.multiplier.entity.business.income.IncomeEntity;
import ru.pet.multiplier.repository.IncomeRepository;

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
        var fromT = Timestamp.from(Instant.ofEpochMilli(1546300800));
        var tillT = Timestamp.valueOf(LocalDateTime.now());

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
