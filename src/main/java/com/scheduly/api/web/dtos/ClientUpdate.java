package com.scheduly.api.web.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Dados para atualização parcial de um cliente")
public record ClientUpdate(
        @Schema(description = "Nome do cliente", example = "Maria Silva") String name,
        @Schema(description = "Email do cliente", example = "maria.silva@email.com")
        @Email(message = "Email inválido") String email,
        @Schema(description = "Telefone do cliente", example = "11987654321")
        @Pattern(regexp = "\\d{10,11}", message = "Telefone deve ter 10 ou 11 dígitos") String phone,
        @Schema(description = "URL do avatar no Cloudinary", example = "https://res.cloudinary.com/...") String avatarUrl,
        @Schema(description = "Endereço do cliente") AddressRequest address) {
}
