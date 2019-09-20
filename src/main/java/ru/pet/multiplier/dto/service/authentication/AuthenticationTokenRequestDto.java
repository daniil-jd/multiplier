package ru.pet.multiplier.dto.service.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationTokenRequestDto {
    private String username;
    private String password;
}
