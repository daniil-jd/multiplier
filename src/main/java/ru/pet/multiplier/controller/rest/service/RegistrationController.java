package ru.pet.multiplier.controller.rest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.pet.multiplier.dto.service.authentication.AuthenticationTokenResponseDto;
import ru.pet.multiplier.dto.service.registration.RegistrationRequestDto;
import ru.pet.multiplier.service.impl.RegistrationService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/registration")
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService service;

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void registration(@Valid @RequestBody RegistrationRequestDto dto) {
        service.register(dto);
    }

    @GetMapping("/confirmation")
    public AuthenticationTokenResponseDto confirmation(@RequestParam String token) {
        return service.confirm(token);
    }

}
