package com.scheduly.api.web.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record AddressResponse (
    String street,
    String number,
    String complement,
    String neighborhood,
    String city,
    String state,
    String zipCode) {
}
