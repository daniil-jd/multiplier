package ru.pet.multiplier.token;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
public class AuthenticationToken extends AbstractAuthenticationToken {
    private final Object principal;
    private final Object credentials;

    public AuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
    }
}
