package ru.pet.multiplier.controller.rest.business;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.pet.multiplier.dto.business.expenses_kind.ExpensesKindRequestDto;
import ru.pet.multiplier.dto.business.expenses_kind.ExpensesKindResponseDto;
import ru.pet.multiplier.service.impl.ExpensesKindService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/expenses/kind")
@RequiredArgsConstructor
public class ExpensesKindController {
    private final ExpensesKindService service;

    @GetMapping
    public List<ExpensesKindResponseDto> getAllExpensesTypes() {
        return service.getAllExpensesTypeDto();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createExpense(@Valid @RequestBody ExpensesKindRequestDto dto) {
        service.save(dto);
    }

}
