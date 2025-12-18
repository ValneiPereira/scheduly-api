package com.scheduly.api.web.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ReviewResponse(
        Long id,
        Long professionalId,
        Integer rating,
        String comment,
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime createdAt) {
}
