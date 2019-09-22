package ru.pet.multiplier.controller.rest.business;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.pet.multiplier.dto.business.expenses.ExpensesCalculateRequestDto;
import ru.pet.multiplier.dto.business.expenses.ExpensesCalculateResponseDto;
import ru.pet.multiplier.dto.business.expenses.ExpensesRequestDto;
import ru.pet.multiplier.dto.business.expenses.ExpensesResponseDto;
import ru.pet.multiplier.service.impl.ExpensesService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpensesController {
    private final ExpensesService service;

    @GetMapping
    public List<ExpensesResponseDto> getAllExpenses() {
        return service.getAllExpensesDto();
    }

    @PostMapping(value = "/calculate")
    public ExpensesCalculateResponseDto getCalculatedExpenses(@Valid @RequestBody ExpensesCalculateRequestDto dto) {
        return service.calculateExpenses(dto);
    }

    @PostMapping
    public ExpensesResponseDto createExpenses(@Valid @RequestBody ExpensesRequestDto dto) {
        return service.save(dto);
    }

}
