package ru.multiplier.repository

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import ru.multiplier.dao.jooq.tables.TelegramUser
import ru.multiplier.dao.jooq.tables.records.TelegramUserRecord
import java.sql.Timestamp
import java.util.*

@Repository
class TelegramUserRepository(
    private val dsl: DSLContext
) {

    fun findUserByUsername(username: String): Optional<TelegramUserRecord> {
        val request = dsl
            .select(TelegramUser.TELEGRAM_USER.asterisk())
            .from(TelegramUser.TELEGRAM_USER)
            .where(TelegramUser.TELEGRAM_USER.USERNAME.eq(username))

        return request.fetchOptionalInto(TelegramUser.TELEGRAM_USER)
    }

    fun create(username: String, userId: Long): Optional<TelegramUserRecord> {
        val result = TelegramUserRecord(UUID.randomUUID(), userId, username)

        val insert = dsl
            .insertInto(TelegramUser.TELEGRAM_USER)
            .set(result)
        val insertResult = insert.onConflictDoNothing().returning().fetchOne()
        return if (insertResult != null) {
            Optional.of(result)
        } else {
            Optional.empty()
        }
    }

}