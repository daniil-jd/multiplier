package ru.pet.multiplier.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pet.multiplier.dto.service.authentication.AuthenticationTokenResponseDto;
import ru.pet.multiplier.dto.service.registration.RegistrationRequestDto;
import ru.pet.multiplier.entity.service.AuthenticationTokenEntity;
import ru.pet.multiplier.entity.service.RegistrationTokenEntity;
import ru.pet.multiplier.entity.service.UserEntity;
import ru.pet.multiplier.exception.authentication.AuthenticationTokenNotFoundException;
import ru.pet.multiplier.exception.registration.TooManyConfirmationRequestsException;
import ru.pet.multiplier.exception.registration.TooManyRegistrationRequestsException;
import ru.pet.multiplier.exception.user.UserAlreadyEnabledException;
import ru.pet.multiplier.exception.user.UsernameAlreadyExistsException;
import ru.pet.multiplier.repository.AuthenticationTokenRepository;
import ru.pet.multiplier.repository.RegistrationTokenRepository;
import ru.pet.multiplier.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class RegistrationService {
    private final UserRepository userRepository;
    private final RegistrationTokenRepository registrationTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final DefaultMailService mailService;
    private final AuthenticationTokenRepository authenticationTokenRepository;

    private final String ROLE_USER = "ROLE_USER";

    public void register(RegistrationRequestDto dto) {
        var tokenValue = UUID.randomUUID().toString();
        var registrationTime = LocalDateTime.now();
        var userOptional = userRepository.findByUsername(dto.getUsername());

        if (userOptional.isEmpty()) {
            var user = new UserEntity(
                    0L,
                    dto.getUsername(),
                    dto.getUsername(),
                    passwordEncoder.encode(dto.getPassword()),
                    List.of(new SimpleGrantedAuthority(ROLE_USER)),
                    true,
                    true,
                    true,
                    false
            );

            var token = new RegistrationTokenEntity(
                    tokenValue,
                    user,
                    registrationTime
            );
            registrationTokenRepository.save(token);

            //mail send to user
            mailService.sendRegistrationToken(user.getUsername(), tokenValue);
        } else {
            if (userOptional.get().isEnabled()) {
                throw new UsernameAlreadyExistsException("api.exception.user.username.already_exist.message");
            }

            if (registrationTokenRepository.findAllByUserId(userOptional.get().getId()).size() >= 3) {
                throw new TooManyRegistrationRequestsException("api.exception.registration.too_many_requests.message");
            }

            var token = new RegistrationTokenEntity(
                    tokenValue,
                    userOptional.get(),
                    registrationTime
            );

            registrationTokenRepository.save(token);

            //mail token send
            mailService.sendRegistrationToken(userOptional.get().getUsername(), tokenValue);
        }


    }

    public AuthenticationTokenResponseDto confirm(String tokenValue) {
        var token = registrationTokenRepository.findById(tokenValue);

        if (token.isEmpty()) {
            throw new AuthenticationTokenNotFoundException("api.exception.authenticate.token.not_found.message");
        }

        var user = token.get().getUser();
        if (registrationTokenRepository.findAllByUserId(user.getId()).size() >= 3) {
            throw new TooManyConfirmationRequestsException("api.exception.registration.confirmation.too_many_requests.message");
        }

        if (user.isEnabled()) {
            throw new UserAlreadyEnabledException("api.exception.user.already_enabled.message");
        }

        user.setEnabled(true);

        var authToken = UUID.randomUUID().toString();
        var tokenEntity = new AuthenticationTokenEntity(authToken, user);
        authenticationTokenRepository.save(tokenEntity);
        return new AuthenticationTokenResponseDto(authToken);
    }

    @Scheduled(fixedRate = 15 * 60 * 1000)
    public void scheduledDeleteRegistrationToken() {
        registrationTokenRepository.deleteRegistrationTokenEntityByTime();
    }
}
