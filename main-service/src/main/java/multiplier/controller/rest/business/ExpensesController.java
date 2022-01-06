package multiplier.controller.rest.business;

import lombok.RequiredArgsConstructor;
import ru.example.common.dto.business.expenses.ExpensesCalculateRequestDto;
import ru.example.common.dto.business.expenses.ExpensesCalculateResponseDto;
import ru.example.common.dto.business.expenses.ExpensesRequestDto;
import ru.example.common.dto.business.expenses.ExpensesResponseDto;
import multiplier.service.impl.ExpensesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/period")
    public List<ExpensesResponseDto> getExpensesByPeriod(
            @RequestParam(required = false) String dateFrom, @RequestParam(required = false) String dateTo) {
        return service.getAllExpensesInPeriod(dateFrom, dateTo);
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
