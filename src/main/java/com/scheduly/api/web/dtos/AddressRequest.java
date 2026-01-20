package com.scheduly.api.web.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddressRequest(
        @NotBlank(message = "Rua é obrigatória")
        String street,
        String number,
        String complement,
        @NotBlank(message = "Bairro é obrigatório")
        String neighborhood,
        @NotBlank(message = "Cidade é obrigatória")
        String city,
        @NotBlank(message = "Estado é obrigatório")
        @Pattern(regexp = "^[A-Z]{2}$", message = "Estado deve conter 2 letras (ex: SP)")
        String state,
        @NotBlank(message = "CEP é obrigatório")
        @Pattern(regexp = "\\d{8}", message = "CEP inválido (deve conter 8 dígitos)")
        String zipCode) {
}
