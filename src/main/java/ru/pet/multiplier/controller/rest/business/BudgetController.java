package ru.pet.multiplier.controller.rest.business;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.pet.multiplier.dto.business.budget.BudgetResponseDto;
import ru.pet.multiplier.service.impl.BudgetaryService;

import java.sql.Timestamp;

@RestController
@RequestMapping("/api/budget")
@RequiredArgsConstructor
public class BudgetController {
    private final BudgetaryService service;

    @GetMapping
    public BudgetResponseDto getBudgetByPeriod(@RequestParam(required = false) Timestamp from, @RequestParam(required = false) Timestamp till) {
        return service.getBudgetByPeriod(from, till);
    }

}
