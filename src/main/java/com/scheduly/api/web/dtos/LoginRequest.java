package com.scheduly.api.web.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Email não deve estar em branco")
        @Email(message = "Email inválido")
        String email,

        @NotBlank(message = "Senha não deve estar em branco")
        String password
) {
}
