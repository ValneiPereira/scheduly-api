package com.scheduly.api.web.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ClientRequest(

        @NotBlank(message = "Nome é obrigatório")
        String name,
        @NotBlank(message = "Email é obrigatório") @Email(message = "Email inválido")
        String email,
        @Pattern(regexp = "\\d{10,11}", message = "Telefone deve ter 10 ou 11 dígitos")
        String phone,
        @Valid
        AddressRequest address){
}