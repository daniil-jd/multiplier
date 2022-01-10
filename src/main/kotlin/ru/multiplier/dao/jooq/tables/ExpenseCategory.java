/*
 * This file is generated by jOOQ.
 */
package ru.multiplier.dao.jooq.tables;


import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row3;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import ru.multiplier.dao.jooq.Keys;
import ru.multiplier.dao.jooq.Public;
import ru.multiplier.dao.jooq.tables.records.ExpenseCategoryRecord;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ExpenseCategory extends TableImpl<ExpenseCategoryRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.expense_category</code>
     */
    public static final ExpenseCategory EXPENSE_CATEGORY = new ExpenseCategory();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ExpenseCategoryRecord> getRecordType() {
        return ExpenseCategoryRecord.class;
    }

    /**
     * The column <code>public.expense_category.id</code>.
     */
    public final TableField<ExpenseCategoryRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.expense_category.name</code>.
     */
    public final TableField<ExpenseCategoryRecord, String> NAME = createField(DSL.name("name"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.expense_category.category</code>.
     */
    public final TableField<ExpenseCategoryRecord, String> CATEGORY = createField(DSL.name("category"), SQLDataType.CLOB.nullable(false), this, "");

    private ExpenseCategory(Name alias, Table<ExpenseCategoryRecord> aliased) {
        this(alias, aliased, null);
    }

    private ExpenseCategory(Name alias, Table<ExpenseCategoryRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.expense_category</code> table reference
     */
    public ExpenseCategory(String alias) {
        this(DSL.name(alias), EXPENSE_CATEGORY);
    }

    /**
     * Create an aliased <code>public.expense_category</code> table reference
     */
    public ExpenseCategory(Name alias) {
        this(alias, EXPENSE_CATEGORY);
    }

    /**
     * Create a <code>public.expense_category</code> table reference
     */
    public ExpenseCategory() {
        this(DSL.name("expense_category"), null);
    }

    public <O extends Record> ExpenseCategory(Table<O> child, ForeignKey<O, ExpenseCategoryRecord> key) {
        super(child, key, EXPENSE_CATEGORY);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public UniqueKey<ExpenseCategoryRecord> getPrimaryKey() {
        return Keys.EXPENSE_CATEGORY_PKEY;
    }

    @Override
    public List<UniqueKey<ExpenseCategoryRecord>> getKeys() {
        return Arrays.<UniqueKey<ExpenseCategoryRecord>>asList(Keys.EXPENSE_CATEGORY_PKEY, Keys.EXPENSE_CATEGORY_NAME_KEY);
    }

    @Override
    public ExpenseCategory as(String alias) {
        return new ExpenseCategory(DSL.name(alias), this);
    }

    @Override
    public ExpenseCategory as(Name alias) {
        return new ExpenseCategory(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ExpenseCategory rename(String name) {
        return new ExpenseCategory(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ExpenseCategory rename(Name name) {
        return new ExpenseCategory(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<UUID, String, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}