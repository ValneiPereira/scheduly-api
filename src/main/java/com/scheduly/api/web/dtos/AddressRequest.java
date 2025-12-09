package com.scheduly.api.web.dtos;

import jakarta.validation.constraints.Pattern;

/**
 * DTO Request para Address
 */
public record AddressRequest(

        String street,
        String number,
        String complement,
        String neighborhood,
        String city,
        String state,
        @Pattern(regexp = "\\d{5}-?\\d{3}", message = "CEP deve estar no formato 00000-000") String zipCode) {
}
