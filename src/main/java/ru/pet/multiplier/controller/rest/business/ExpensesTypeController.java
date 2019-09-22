package ru.pet.multiplier.controller.rest.business;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pet.multiplier.dto.business.expenses_type.ExpensesTypeRequestDto;
import ru.pet.multiplier.dto.business.expenses_type.ExpensesTypeResponseDto;
import ru.pet.multiplier.service.impl.ExpensesTypeService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/expenses/type")
@RequiredArgsConstructor
public class ExpensesTypeController {
    private final ExpensesTypeService service;

    @GetMapping
    public List<ExpensesTypeResponseDto> getAllExpensesTypes() {
        return service.getAllExpensesTypeDto();
    }

    @PostMapping
    public ExpensesTypeResponseDto createExpense(@Valid @RequestBody ExpensesTypeRequestDto dto) {
        return service.save(dto);
    }

}
