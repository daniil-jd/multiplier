package ru.pet.multiplier.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pet.multiplier.dto.service.authentication.AuthenticationTokenRequestDto;
import ru.pet.multiplier.dto.service.authentication.AuthenticationTokenResponseDto;
import ru.pet.multiplier.entity.service.AuthenticationTokenEntity;
import ru.pet.multiplier.exception.user.BadCredentialsException;
import ru.pet.multiplier.exception.user.UserDoesNotExistException;
import ru.pet.multiplier.repository.AuthenticationTokenRepository;
import ru.pet.multiplier.repository.UserRepository;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationTokenRepository authenticationTokenRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationTokenResponseDto authenticate(AuthenticationTokenRequestDto dto) {
        var userEntity = userRepository
                .findByUsername(dto.getUsername())
                .orElseThrow(() -> new UserDoesNotExistException("api.exception.user.not_exist.message")
        );

        if (!passwordEncoder.matches(dto.getPassword(), userEntity.getPassword())) {
            throw new BadCredentialsException("api.exception.bad_credentials.message");
        }

        var token = UUID.randomUUID().toString();
        var tokenEntity = new AuthenticationTokenEntity(token, userEntity);
        authenticationTokenRepository.deleteAllByUser(userEntity);
        authenticationTokenRepository.save(tokenEntity);

        return new AuthenticationTokenResponseDto(token);
    }

}
