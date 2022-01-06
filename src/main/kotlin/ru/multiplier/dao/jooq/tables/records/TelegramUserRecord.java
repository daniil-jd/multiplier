/*
 * This file is generated by jOOQ.
 */
package ru.multiplier.dao.jooq.tables.records;


import java.util.UUID;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;

import ru.multiplier.dao.jooq.tables.TelegramUser;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TelegramUserRecord extends UpdatableRecordImpl<TelegramUserRecord> implements Record3<UUID, Long, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.telegram_user.id</code>.
     */
    public void setId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.telegram_user.id</code>.
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>public.telegram_user.telegram_user_id</code>.
     */
    public void setTelegramUserId(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.telegram_user.telegram_user_id</code>.
     */
    public Long getTelegramUserId() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>public.telegram_user.username</code>.
     */
    public void setUsername(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.telegram_user.username</code>.
     */
    public String getUsername() {
        return (String) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<UUID, Long, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<UUID, Long, String> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return TelegramUser.TELEGRAM_USER.ID;
    }

    @Override
    public Field<Long> field2() {
        return TelegramUser.TELEGRAM_USER.TELEGRAM_USER_ID;
    }

    @Override
    public Field<String> field3() {
        return TelegramUser.TELEGRAM_USER.USERNAME;
    }

    @Override
    public UUID component1() {
        return getId();
    }

    @Override
    public Long component2() {
        return getTelegramUserId();
    }

    @Override
    public String component3() {
        return getUsername();
    }

    @Override
    public UUID value1() {
        return getId();
    }

    @Override
    public Long value2() {
        return getTelegramUserId();
    }

    @Override
    public String value3() {
        return getUsername();
    }

    @Override
    public TelegramUserRecord value1(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public TelegramUserRecord value2(Long value) {
        setTelegramUserId(value);
        return this;
    }

    @Override
    public TelegramUserRecord value3(String value) {
        setUsername(value);
        return this;
    }

    @Override
    public TelegramUserRecord values(UUID value1, Long value2, String value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TelegramUserRecord
     */
    public TelegramUserRecord() {
        super(TelegramUser.TELEGRAM_USER);
    }

    /**
     * Create a detached, initialised TelegramUserRecord
     */
    public TelegramUserRecord(UUID id, Long telegramUserId, String username) {
        super(TelegramUser.TELEGRAM_USER);

        setId(id);
        setTelegramUserId(telegramUserId);
        setUsername(username);
    }
}
