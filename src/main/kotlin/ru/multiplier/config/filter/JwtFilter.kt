package ru.multiplier.config.filter

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean
import ru.multiplier.dao.CustomUserDetails
import ru.multiplier.service.JwtProviderService
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

@Component
class JwtFilter(
    private val jwtProviderService: JwtProviderService
) : GenericFilterBean() {

    companion object {
        const val AUTHORIZATION = "Authorization"
        const val BEARER = "Bearer "
    }

    override fun doFilter(
        servletRequest: ServletRequest, servletResponse: ServletResponse, filterChain: FilterChain
    ) {
        val token = getTokenFromRequest(servletRequest as HttpServletRequest)
        if (token.isPresent && jwtProviderService.validateToken(token.get())) {
            val login = jwtProviderService.getDataFromToken(token.get()).subject
            val customUserDetails = CustomUserDetails(login, login, mutableListOf())
            val authToken = UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.authorities)
            SecurityContextHolder.getContext().authentication = authToken
        }
        filterChain.doFilter(servletRequest, servletResponse)
    }

    private fun getTokenFromRequest(request: HttpServletRequest): Optional<String> {
        val bearer = request.getHeader(AUTHORIZATION)
        if (!bearer.isNullOrEmpty() && bearer.startsWith(BEARER)) {
            return Optional.of(bearer.substring(BEARER.length))
        }
        return Optional.empty()
    }
}