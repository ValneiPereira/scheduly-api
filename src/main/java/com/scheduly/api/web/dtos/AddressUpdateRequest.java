package com.scheduly.api.web.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para atualizar endereço do profissional")
public record AddressUpdateRequest(
        @Schema(description = "Rua", example = "Rua das Flores")
        @NotBlank(message = "Rua é obrigatória")
        @Size(min = 1, max = 255, message = "Rua deve ter entre 1 e 255 caracteres")
        String street,

        @Schema(description = "Número", example = "123")
        String number,

        @Schema(description = "Complemento", example = "Apto 45")
        String complement,

        @Schema(description = "Bairro", example = "Centro")
        @NotBlank(message = "Bairro é obrigatório")
        @Size(min = 1, max = 255, message = "Bairro deve ter entre 1 e 255 caracteres")
        String neighborhood,

        @Schema(description = "Cidade", example = "São Paulo")
        @NotBlank(message = "Cidade é obrigatória")
        @Size(min = 1, max = 255, message = "Cidade deve ter entre 1 e 255 caracteres")
        String city,

        @Schema(description = "Estado (UF)", example = "SP")
        @NotBlank(message = "Estado é obrigatório")
        @Pattern(regexp = "^[A-Z]{2}$", message = "Estado deve conter 2 letras maiúsculas (ex: SP)")
        String state,

        @Schema(description = "CEP", example = "01310-100")
        @NotBlank(message = "CEP é obrigatório")
        @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP inválido (formato: 12345-678)")
        String zipCode
) {
}
