package multiplier.service.impl;

import lombok.RequiredArgsConstructor;
import ru.example.common.dto.service.authentication.AuthenticationTokenRequestDto;
import ru.example.common.dto.service.authentication.AuthenticationTokenResponseDto;
import multiplier.entity.service.AuthenticationTokenEntity;
import multiplier.entity.service.UserEntity;
import multiplier.exception.user.BadCredentialsException;
import multiplier.exception.user.UserDoesNotExistException;
import multiplier.repository.AuthenticationTokenRepository;
import multiplier.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationTokenRepository authenticationTokenRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationTokenResponseDto authenticate(AuthenticationTokenRequestDto dto) {
        UserEntity userEntity = userRepository
                .findByUsername(dto.getUsername())
                .orElseThrow(() -> new UserDoesNotExistException("api.exception.user.not_exist.message")
        );

        if (!passwordEncoder.matches(dto.getPassword(), userEntity.getPassword())) {
            throw new BadCredentialsException("api.exception.bad_credentials.message");
        }

        String token = UUID.randomUUID().toString();
        AuthenticationTokenEntity tokenEntity = new AuthenticationTokenEntity(token, userEntity);
        authenticationTokenRepository.deleteAllByUser(userEntity);
        authenticationTokenRepository.save(tokenEntity);

        return new AuthenticationTokenResponseDto(token);
    }

}
