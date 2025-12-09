package com.scheduly.api.web.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.scheduly.model.AddressResponse;

import java.time.LocalDateTime;

/**
 * DTO Response para Client
 */
public record ClientResponse(
                Long id,
                String name,
                String email,
                String cpf,
                String phone,
                AddressResponse address,
                @JsonFormat(pattern = "dd-MM-yyyy") LocalDateTime createdAt,
                @JsonFormat(pattern = "dd-MM-yyyy") LocalDateTime updatedAt) {
}
