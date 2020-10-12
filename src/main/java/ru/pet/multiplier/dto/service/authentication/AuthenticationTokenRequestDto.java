package ru.pet.multiplier.dto.service.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationTokenRequestDto {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
