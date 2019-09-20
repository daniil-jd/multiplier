package ru.pet.multiplier.dto.service.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequestDto {
    @Email
    @NotNull
    private String username;
    @NotNull
    private String password;
}
