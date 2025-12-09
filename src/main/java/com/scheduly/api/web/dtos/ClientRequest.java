package com.scheduly.api.web.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;

/**
 * DTO Request para Client
 */
public record ClientRequest(

        @NotBlank(message = "Nome é obrigatório") String name,

        @NotBlank(message = "Email é obrigatório") @Email(message = "Email inválido") String email,

        @NotBlank(message = "CPF é obrigatório") @CPF(message = "CPF inválido") String cpf,

        @Pattern(regexp = "\\d{10,11}", message = "Telefone deve ter 10 ou 11 dígitos") String phone,

        @Valid AddressRequest address) {
}
