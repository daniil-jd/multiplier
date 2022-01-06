package multiplier.service.impl;

import lombok.RequiredArgsConstructor;
import multiplier.entity.service.AuthenticationTokenEntity;
import multiplier.entity.service.UserEntity;
import multiplier.exception.authentication.AuthenticateTokenException;
import multiplier.repository.AuthenticationTokenRepository;
import multiplier.service.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DefaultTokenService implements TokenService {
    private final AuthenticationTokenRepository authenticationTokenRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Object token = authentication.getPrincipal();

        if (token == null) {
            throw new AuthenticateTokenException("api.exception.authenticate.token.null.message");
        }
        AuthenticationTokenEntity tokenEntity = authenticationTokenRepository
                .findById(token.toString())
                .orElseThrow(() -> new AuthenticateTokenException("api.exception.authenticate.token.invalid.message"));
        UserEntity userEntity = tokenEntity.getUser();

        return new UsernamePasswordAuthenticationToken(
                userEntity,
                userEntity.getPassword(),
                userEntity.getAuthorities()
        );
    }
}
