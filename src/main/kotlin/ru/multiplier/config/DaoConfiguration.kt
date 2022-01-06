package ru.multiplier.config

import com.zaxxer.hikari.HikariDataSource
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DaoConfiguration {

    @Bean
    fun dslContext(dataSource: HikariDataSource): DSLContext
            = DSL.using(dataSource, SQLDialect.POSTGRES)
}