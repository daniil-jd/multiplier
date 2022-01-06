package ru.multiplier.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.multiplier.dao.jooq.tables.records.TelegramUserRecord
import ru.multiplier.repository.TelegramUserRepository
import java.util.*

@Service
class TelegramUserService(
    private val telegramUserRepository: TelegramUserRepository
) {

    @Transactional
    fun findUserByUsername(username: String): Optional<TelegramUserRecord>
            = telegramUserRepository.findUserByUsername(username)

    @Transactional
    fun create(username: String, userId: Long): TelegramUserRecord
            = telegramUserRepository.create(username, userId)

    fun auth(username: String) {
        val userRecord = findUserByUsername(username)

    }
}