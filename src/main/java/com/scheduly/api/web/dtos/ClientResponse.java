package com.scheduly.api.web.dtos;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ClientResponse(
        Long id,
        String name,
        String email,
        String phone,
        String avatarUrl,
        AddressResponse address,
        @JsonFormat(pattern = "dd-MM-yyyy") LocalDateTime createdAt,
        @JsonFormat(pattern = "dd-MM-yyyy") LocalDateTime updatedAt) {
}
