package multiplier.controller.rest.business;

import lombok.RequiredArgsConstructor;
import ru.example.common.dto.business.income.IncomeRequestDto;
import ru.example.common.dto.business.income.IncomeResponseDto;
import multiplier.service.impl.IncomeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/income")
@RequiredArgsConstructor
public class IncomeController {
    private final IncomeService service;

    @GetMapping
    public List<IncomeResponseDto> getAllIncomeInPeriod(@RequestParam(required = false) Timestamp from, @RequestParam(required = false) Timestamp till) {
        return service.getAllIncomeInPeriod(from, till);
    }

    @PostMapping
    public IncomeResponseDto createIncome(@RequestBody @Valid IncomeRequestDto incomeRequestDto) {
        return service.createIncome(incomeRequestDto);
    }

}
