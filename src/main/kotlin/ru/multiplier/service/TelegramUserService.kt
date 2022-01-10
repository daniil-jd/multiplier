package ru.multiplier.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.multiplier.dao.jooq.tables.records.TelegramUserRecord
import ru.multiplier.dto.AuthResponseDto
import ru.multiplier.dto.ExceptionDto
import ru.multiplier.repository.TelegramUserRepository
import java.util.*

@Service
class TelegramUserService(
    private val telegramUserRepository: TelegramUserRepository,
    private val jwtProviderService: JwtProviderService
) {

    @Transactional
    fun findUserByUsername(username: String): Optional<TelegramUserRecord>
            = telegramUserRepository.findUserByUsername(username)

    @Transactional
    fun create(username: String, userId: Long): Optional<TelegramUserRecord>
            = telegramUserRepository.create(username, userId)

    @Transactional(readOnly = true)
    fun auth(username: String): Optional<AuthResponseDto> {
        val userRecord = findUserByUsername(username)
        return if (userRecord.isPresent) {
            val token = jwtProviderService.generateToken(username)
            Optional.of(AuthResponseDto(token))
        } else {
            Optional.empty()
        }
    }
}