package multiplier.token.filter;

import lombok.RequiredArgsConstructor;
import multiplier.service.TokenService;
import multiplier.token.AuthenticationToken;
import multiplier.token.entrypoint.TokenAuthenticationEntryPoint;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class DefaultTokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final TokenAuthenticationEntryPoint entryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("X-Chat-Token");

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            AuthenticationToken authRequest = new AuthenticationToken(token, null);
            Authentication authResult = tokenService.authenticate(authRequest);

            SecurityContextHolder.getContext().setAuthentication(authResult);
        } catch (AuthenticationException e) {
            SecurityContextHolder.clearContext();
            entryPoint.commence(request, response, e);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
