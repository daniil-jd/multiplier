package ru.multiplier

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MultiplierApplication

fun main(args: Array<String>) {
	runApplication<MultiplierApplication>(*args)
	System.setProperty("org.jooq.no-logo", "true")
}
