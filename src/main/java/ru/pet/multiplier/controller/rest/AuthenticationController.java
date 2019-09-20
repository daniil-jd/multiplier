package ru.pet.multiplier.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pet.multiplier.dto.service.authentication.AuthenticationTokenRequestDto;
import ru.pet.multiplier.dto.service.authentication.AuthenticationTokenResponseDto;
import ru.pet.multiplier.service.impl.AuthenticationService;

@RestController
@RequestMapping("/api/authentication")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping
    public AuthenticationTokenResponseDto authenticate(@RequestBody AuthenticationTokenRequestDto dto) {
        return service.authenticate(dto);
    }
}
