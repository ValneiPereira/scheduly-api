package com.scheduly.api.web.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponse(
        String message,
        Integer status,
        String error,
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime timestamp) {
}
