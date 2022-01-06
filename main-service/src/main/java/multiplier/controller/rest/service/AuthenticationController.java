package multiplier.controller.rest.service;


import ru.example.common.dto.service.authentication.AuthenticationTokenRequestDto;
import ru.example.common.dto.service.authentication.AuthenticationTokenResponseDto;
import lombok.RequiredArgsConstructor;
import multiplier.service.impl.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/authentication")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping
    public AuthenticationTokenResponseDto authenticate(@Valid @RequestBody AuthenticationTokenRequestDto dto) {
        return service.authenticate(dto);
    }
}
