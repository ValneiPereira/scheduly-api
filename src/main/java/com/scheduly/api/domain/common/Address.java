package com.scheduly.api.domain.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String street; // Rua
    private String number; // Número
    private String complement; // Complemento
    private String neighborhood; // Bairro
    private String city; // Cidade
    private String state; // Estado (UF)
    private String zipCode; // CEP
    private String country; // País (padrão: Brasil)
}
