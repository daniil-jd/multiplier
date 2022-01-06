package ru.multiplier.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.multiplier.dto.AuthDto
import ru.multiplier.dto.RegisterDto
import ru.multiplier.service.JwtProviderService
import ru.multiplier.service.TelegramUserService

@RestController
class AuthController(
    private val telegramUserService: TelegramUserService,
    private val jwtProviderService: JwtProviderService
) {

    @PostMapping("/register")
    fun register(@RequestBody registerDto: RegisterDto): ResponseEntity<*> {
        telegramUserService.create(registerDto.username, registerDto.userId)
        return ResponseEntity.ok() as ResponseEntity<*>
    }

    @PostMapping("/auth")
    fun auth(@RequestBody authDto: AuthDto): ResponseEntity<*> {
//        telegramUserService.
        return ResponseEntity.ok() as ResponseEntity<*>
    }
}