package ru.multiplier.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.multiplier.dto.AuthDto
import ru.multiplier.dto.ExceptionDto
import ru.multiplier.dto.RegisterDto
import ru.multiplier.service.TelegramUserService
import java.util.*

@RestController
class AuthController(
    private val telegramUserService: TelegramUserService
) {

    @PostMapping("/register")
    fun register(@RequestBody registerDto: RegisterDto): ResponseEntity<*> {
        val response = telegramUserService.create(registerDto.username, registerDto.userId)
        return buildRegisterResponseEntity(response)
    }

    @PostMapping("/auth")
    fun auth(@RequestBody authDto: AuthDto): ResponseEntity<*> {
        val response = telegramUserService.auth(authDto.username)
        ExceptionDto(404, "user not found")
        return buildAuthResponseEntity(response)
    }

    fun buildAuthResponseEntity(result: Optional<*>): ResponseEntity<*> {
        return if (result.isPresent) {
            ResponseEntity.ok(result)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionDto(HttpStatus.NOT_FOUND.value(), "user not found"))
        }
    }

    fun buildRegisterResponseEntity(result: Optional<*>): ResponseEntity<*> {
        return if (result.isPresent) {
            ResponseEntity.ok().build<String>()
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ExceptionDto(HttpStatus.BAD_REQUEST.value(), "user already exists"))
        }
    }
}