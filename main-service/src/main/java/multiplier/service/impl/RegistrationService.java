package multiplier.service.impl;

import lombok.RequiredArgsConstructor;
import ru.example.common.dto.service.authentication.AuthenticationTokenResponseDto;
import ru.example.common.dto.service.registration.RegistrationRequestDto;
import multiplier.entity.service.AuthenticationTokenEntity;
import multiplier.entity.service.RegistrationTokenEntity;
import multiplier.entity.service.UserEntity;
import multiplier.exception.authentication.AuthenticationTokenNotFoundException;
import multiplier.exception.registration.TooManyConfirmationRequestsException;
import multiplier.exception.registration.TooManyRegistrationRequestsException;
import multiplier.exception.user.UserAlreadyEnabledException;
import multiplier.exception.user.UsernameAlreadyExistsException;
import multiplier.repository.AuthenticationTokenRepository;
import multiplier.repository.RegistrationTokenRepository;
import multiplier.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
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
        String tokenValue = UUID.randomUUID().toString();
        LocalDateTime registrationTime = LocalDateTime.now();
        Optional<UserEntity> userOptional = userRepository.findByUsername(dto.getUsername());

        if (!userOptional.isPresent()) {
            UserEntity user = new UserEntity(
                    0L,
                    dto.getUsername(),
                    dto.getUsername(),
                    passwordEncoder.encode(dto.getPassword()),
                    Arrays.asList(new SimpleGrantedAuthority(ROLE_USER)),
                    true,
                    true,
                    true,
                    false
            );

            RegistrationTokenEntity token = new RegistrationTokenEntity(
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

            RegistrationTokenEntity token = new RegistrationTokenEntity(
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
        Optional<RegistrationTokenEntity> token = registrationTokenRepository.findById(tokenValue);

        if (!token.isPresent()) {
            throw new AuthenticationTokenNotFoundException("api.exception.authenticate.token.not_found.message");
        }

        UserEntity user = token.get().getUser();
        if (registrationTokenRepository.findAllByUserId(user.getId()).size() >= 3) {
            throw new TooManyConfirmationRequestsException("api.exception.registration.confirmation.too_many_requests.message");
        }

        if (user.isEnabled()) {
            throw new UserAlreadyEnabledException("api.exception.user.already_enabled.message");
        }

        user.setEnabled(true);

        String authToken = UUID.randomUUID().toString();
        AuthenticationTokenEntity tokenEntity = new AuthenticationTokenEntity(authToken, user);
        authenticationTokenRepository.save(tokenEntity);
        return new AuthenticationTokenResponseDto(authToken);
    }

    @Scheduled(fixedRate = 15 * 60 * 1000)
    public void scheduledDeleteRegistrationToken() {
        registrationTokenRepository.deleteRegistrationTokenEntityByTime();
    }
}
