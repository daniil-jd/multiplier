package ru.multiplier.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import ru.multiplier.dao.CustomUserDetails

@Service
class CustomUserDetailsService(
    private val telegramUserService: TelegramUserService
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val tlgUser = telegramUserService.findUserByUsername(username)
            .orElseThrow{ UsernameNotFoundException(username) }

        return CustomUserDetails(
            tlgUser.telegramUserId.toString(), tlgUser.telegramUserId.toString(), mutableListOf()
        )
    }
}